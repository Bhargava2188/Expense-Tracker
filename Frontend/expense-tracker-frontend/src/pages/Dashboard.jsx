import { useEffect, useState } from "react";
import api from "../api/axios";
import Layout from "../components/Layout";
import SummaryCard from "../components/SummaryCard";
import ExpensePieChart from "../components/ExpensePieChart";
import MonthlyBarChart from "../components/MonthlyBarChart";
import RecentTransactions from "../components/RecentTransactions";

function Dashboard() {

    const [dashboard, setDashboard] = useState(null);
    const [expenses, setExpenses] = useState([]);
    const [income, setIncome] = useState([]);

    useEffect(() => {

        api.get("/dashboard")
            .then((res) => setDashboard(res.data))
            .catch(console.error);

        api.get("/expenses")
            .then((res) => setExpenses(res.data));

        api.get("/income")
            .then(res => setIncome(res.data));

    }, []);

    if (!dashboard) {

        return (

            <Layout>

                <h2>Loading...</h2>

            </Layout>

        );

    }

    return (

        <Layout>

            <h1>Dashboard</h1>

            <div
                style={{
                    display: "flex",
                    gap: "20px",
                    marginTop: "20px",
                    flexWrap: "wrap"
                }}
            >

                <SummaryCard
                    title="Balance"
                    value={dashboard.balance}
                    color="#1976d2"
                />

                <SummaryCard
                    title="Income"
                    value={dashboard.totalIncome}
                    color="#4CAF50"
                />

                <SummaryCard
                    title="Expense"
                    value={dashboard.totalExpense}
                    color="#f44336"
                />

                <h2>Expense Category Analysis</h2>

                <ExpensePieChart expenses={expenses} />

            </div>

            <br />
            <h2>Income vs Expense</h2>

            <MonthlyBarChart

            income={dashboard.totalIncome}

            expense={dashboard.totalExpense}

            />

            <h2>Statistics</h2>

            <p>Total Income Transactions : {dashboard.incomeCount}</p>

            <p>Total Expense Transactions : {dashboard.expenseCount}</p>


            <RecentTransactions
                expenses={expenses}
                incomes={income}
            />

        </Layout>

    );

}

export default Dashboard;