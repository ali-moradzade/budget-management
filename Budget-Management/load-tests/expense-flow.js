import http from "k6/http";
import {check, sleep} from "k6";

export const options = {
    stages: [
        {duration: "20s", target: 10},
        {duration: "40s", target: 30},
        {duration: "40s", target: 50},
        {duration: "20s", target: 0},
    ],

    thresholds: {
        http_req_failed: ["rate<0.01"],
        http_req_duration: ["p(95)<500"],
    },
};

const BASE_URL = "http://localhost:8080";

export default function () {

    // UNIQUE PER VU (fix collision problem)
    const suffix = `${__VU}_${__ITER}_${Date.now()}`;

    // 1. CREATE USER
    let userRes = http.post(
        `${BASE_URL}/users`,
        `username=user_${suffix}`,
        {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
        }
    );

    check(userRes, {
        "user created": (r) => r.status === 201,
    });

    if (userRes.status !== 201) return;

    let userId = userRes.body;

    // 2. CREATE CATEGORY (ONLY IF USER EXISTS)
    let catRes = http.post(
        `${BASE_URL}/categories/${userId}`,
        null,
        {
            params: {
                categoryName: "food"
            }
        }
    );

    check(catRes, {
        "category created": (r) => r.status === 201,
    });

    if (catRes.status !== 201) return;

    // 3. CREATE EXPENSE
    let expenseRes = http.post(
        `${BASE_URL}/expenses/${userId}`,
        JSON.stringify({
            category: "food",
            amount: 100,
            description: "load test",
            friendIds: []
        }),
        {
            headers: {
                "Content-Type": "application/json",
            },
        }
    );

    check(expenseRes, {
        "expense created": (r) => r.status === 201,
    });

    // 4. VERIFY
    let getRes = http.get(`${BASE_URL}/expenses/${userId}`);

    check(getRes, {
        "fetch success": (r) => r.status === 200,
    });

    sleep(1);
}