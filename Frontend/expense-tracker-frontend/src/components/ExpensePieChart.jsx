import {
    PieChart,
    Pie,
    Tooltip,
    Legend,
    ResponsiveContainer
} from "recharts";

function ExpensePieChart({ expenses }) {

    const categoryMap = {};

    expenses.forEach((expense) => {

        if (categoryMap[expense.category]) {

            categoryMap[expense.category] += Number(expense.amount);

        } else {

            categoryMap[expense.category] = Number(expense.amount);

        }

    });

    const data = Object.keys(categoryMap).map(category => ({

        name: category,
        value: categoryMap[category]

    }));

    return (

        <ResponsiveContainer width="100%" height={350}>

            <PieChart>

                <Pie
                    data={data}
                    dataKey="value"
                    nameKey="name"
                    outerRadius={120}
                    label
                />

                <Tooltip />

                <Legend />

            </PieChart>

        </ResponsiveContainer>

    );

}

export default ExpensePieChart;