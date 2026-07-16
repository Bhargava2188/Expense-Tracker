import React from "react";

function RecentTransactions({ expenses, incomes }) {

    return (
        <div style={{ marginTop: "30px" }}>

            <h2>Recent Transactions</h2>

            <table border="1" cellPadding="10" width="100%">

                <thead>
                    <tr>
                        <th>Type</th>
                        <th>Title</th>
                        <th>Amount</th>
                        <th>Date</th>
                    </tr>
                </thead>

                <tbody>

                    {expenses.map(expense => (

                        <tr key={"e" + expense.id}>

                            <td>Expense</td>

                            <td>{expense.title}</td>

                            <td style={{ color: "red" }}>
                                ₹ {expense.amount}
                            </td>

                            <td>{expense.date}</td>

                        </tr>

                    ))}

                    {incomes.map(income => (

                        <tr key={"i" + income.id}>

                            <td>Income</td>

                            <td>{income.title}</td>

                            <td style={{ color: "green" }}>
                                ₹ {income.amount}
                            </td>

                            <td>{income.date}</td>

                        </tr>

                    ))}

                </tbody>

            </table>

        </div>
    );

}

export default RecentTransactions;