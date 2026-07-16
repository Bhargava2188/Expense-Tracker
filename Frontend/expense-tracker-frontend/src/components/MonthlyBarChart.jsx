import {
    BarChart,
    Bar,
    XAxis,
    YAxis,
    Tooltip,
    ResponsiveContainer,
    CartesianGrid,
    Legend
} from "recharts";

function MonthlyBarChart({ income, expense }) {

    const data = [

        {
            name: "Finance",
            Income: income,
            Expense: expense
        }

    ];

    return (

        <ResponsiveContainer width="100%" height={350}>

            <BarChart data={data}>

                <CartesianGrid strokeDasharray="3 3"/>

                <XAxis dataKey="name"/>

                <YAxis/>

                <Tooltip/>

                <Legend/>

                <Bar dataKey="Income"/>

                <Bar dataKey="Expense"/>

            </BarChart>

        </ResponsiveContainer>

    );

}

export default MonthlyBarChart;