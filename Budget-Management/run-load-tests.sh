#!/bin/bash

set -e

echo "Running Expense Flow Load Test..."

k6 run load-tests/expense-flow.js