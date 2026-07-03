package project.model;

import jakarta.persistence.*;
import project.dto.response.ExpenseParticipantResponse;
import java.math.BigDecimal;

@Entity
@Table(name = "expense_participants")
public class ExpenseParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "share_amount", nullable = false, precision = 19, scale = 2)
    private BigDecimal shareAmount;

    @Column(name = "is_paid", nullable = false)
    private Boolean paid = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "expense_id", nullable = false)
    private Expense expense;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "friend_id", nullable = false)
    private Friend friend;

    public ExpenseParticipant() {
    }

    public ExpenseParticipant(Double shareAmount, Boolean paid, Expense expense, Friend friend) {
        this.shareAmount = new BigDecimal(shareAmount);
        this.paid = paid;
        this.expense = expense;
        this.friend = friend;
    }

    public ExpenseParticipantResponse toDto() {
        return new ExpenseParticipantResponse(id, shareAmount, paid, friend.getFriendName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getShareAmount() {
        return shareAmount;
    }

    public void setShareAmount(BigDecimal shareAmount) {
        this.shareAmount = shareAmount;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }
}
