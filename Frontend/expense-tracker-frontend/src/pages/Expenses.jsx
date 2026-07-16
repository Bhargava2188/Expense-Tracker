import { useEffect, useState } from "react";
import api from "../api/axios";
import Layout from "../components/Layout";

function Expenses() {

    const [expenses, setExpenses] = useState([]);
    const [editingId, setEditingId] = useState(null);
    const [search, setSearch] = useState("");

    const [form, setForm] = useState({
        title: "",
        amount: "",
        category: "",
        date: "",
        description: ""
    });

    useEffect(() => {
        fetchExpenses();
    }, []);

    const fetchExpenses = async () => {

        try {

            const res = await api.get("/expenses");

            setExpenses(res.data);

        } catch (err) {

            console.error(err);

        }

    };

    const handleChange = (e) => {

        setForm({
            ...form,
            [e.target.name]: e.target.value
        });

    };

    const editExpense = (expense) => {

        setEditingId(expense.id);

        setForm({
            title: expense.title,
            amount: expense.amount,
            category: expense.category,
            date: expense.date,
            description: expense.description
        });

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    };

    const cancelEdit = () => {

        setEditingId(null);

        setForm({
            title: "",
            amount: "",
            category: "",
            date: "",
            description: ""
        });

    };

    const addExpense = async (e) => {

        e.preventDefault();

        try {

            if (editingId) {

                await api.put(`/expenses/${editingId}`, form);

                alert("Expense Updated Successfully");

                setEditingId(null);

            } else {

                await api.post("/expenses", form);

                alert("Expense Added Successfully");

            }

            setForm({
                title: "",
                amount: "",
                category: "",
                date: "",
                description: ""
            });

            fetchExpenses();

        } catch (err) {

            console.error(err);

            alert("Operation Failed");

        }

    };

    const deleteExpense = async (id) => {

        if (!window.confirm("Delete this expense?")) return;

        try {

            await api.delete(`/expenses/${id}`);

            fetchExpenses();

        } catch (err) {

            console.error(err);

        }

    };

    return (

        <Layout>

            <h1>Expenses</h1>

            <form onSubmit={addExpense}>

                <input
                    name="title"
                    placeholder="Title"
                    value={form.title}
                    onChange={handleChange}
                />

                <input
                    name="amount"
                    type="number"
                    placeholder="Amount"
                    value={form.amount}
                    onChange={handleChange}
                />

                <input
                    name="category"
                    placeholder="Category"
                    value={form.category}
                    onChange={handleChange}
                />

                <input
                    name="date"
                    type="date"
                    value={form.date}
                    onChange={handleChange}
                />

                <input
                    name="description"
                    placeholder="Description"
                    value={form.description}
                    onChange={handleChange}
                />

                <button type="submit">
                    {editingId ? "Update Expense" : "Add Expense"}
                </button>

                {editingId && (
                    <button
                        type="button"
                        onClick={cancelEdit}
                        style={{ marginLeft: "10px" }}
                    >
                        Cancel
                    </button>
                )}

            </form>

            <br />

            <input
                type="text"
                placeholder="Search by title..."
                value={search}
                onChange={(e) => setSearch(e.target.value)}
                style={{
                    width: "300px",
                    padding: "8px",
                    marginBottom: "20px"
                }}
            />

            <table border="1" cellPadding="10" width="100%">

                <thead>

                    <tr>
                        <th>Title</th>
                        <th>Amount</th>
                        <th>Category</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>

                </thead>

                <tbody>

                    {expenses
                        .filter((expense) =>
                            expense.title
                                .toLowerCase()
                                .includes(search.toLowerCase())
                        )
                        .map((expense) => (

                            <tr key={expense.id}>

                                <td>{expense.title}</td>

                                <td>₹ {expense.amount}</td>

                                <td>{expense.category}</td>

                                <td>{expense.date}</td>

                                <td>

                                    <button
                                        onClick={() => editExpense(expense)}
                                    >
                                        Edit
                                    </button>

                                    <button
                                        onClick={() => deleteExpense(expense.id)}
                                        style={{ marginLeft: "10px" }}
                                    >
                                        Delete
                                    </button>

                                </td>

                            </tr>

                        ))}

                </tbody>

            </table>

        </Layout>

    );

}

export default Expenses;