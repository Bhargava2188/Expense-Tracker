import { useEffect, useState } from "react";
import api from "../api/axios";
import Layout from "../components/Layout";

function Income() {

    const [incomeList, setIncomeList] = useState([]);
    const [editingId, setEditingId] = useState(null);
    const [search, setSearch] = useState("");

    const [form, setForm] = useState({
        source: "",
        amount: "",
        date: "",
        description: ""
    });

    useEffect(() => {
        fetchIncome();
    }, []);

    const fetchIncome = async () => {

        try {

            const res = await api.get("/income");

            setIncomeList(res.data);

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

    const editIncome = (income) => {

        setEditingId(income.id);

        setForm({
            source: income.source,
            amount: income.amount,
            date: income.date,
            description: income.description
        });

        window.scrollTo({
            top: 0,
            behavior: "smooth"
        });

    };

    const cancelEdit = () => {

        setEditingId(null);

        setForm({
            source: "",
            amount: "",
            date: "",
            description: ""
        });

    };

    const addIncome = async (e) => {

        e.preventDefault();

        try {

            if (editingId) {

                await api.put(`/income/${editingId}`, form);

                alert("Income Updated Successfully");

                setEditingId(null);

            } else {

                await api.post("/income", form);

                alert("Income Added Successfully");

            }

            setForm({
                source: "",
                amount: "",
                date: "",
                description: ""
            });

            fetchIncome();

        } catch (err) {

            console.log(err);

            console.log(err.response);

            console.log(err.response?.data);

            alert(JSON.stringify(err.response?.data));

        }

    };

    const deleteIncome = async (id) => {

        if (!window.confirm("Delete this income?")) return;

        try {

            await api.delete(`/income/${id}`);

            fetchIncome();

        } catch (err) {

            console.log(err);

        }

    };

    return (

        <Layout>

            <h1>Income</h1>

            <form onSubmit={addIncome}>

                <input
                    type="text"
                    name="source"
                    placeholder="Income Source"
                    value={form.source}
                    onChange={handleChange}
                    required
                />

                <input
                    type="number"
                    name="amount"
                    placeholder="Amount"
                    value={form.amount}
                    onChange={handleChange}
                    required
                />

                <input
                    type="date"
                    name="date"
                    value={form.date}
                    onChange={handleChange}
                    required
                />

                <input
                    type="text"
                    name="description"
                    placeholder="Description"
                    value={form.description}
                    onChange={handleChange}
                />

                <button type="submit">
                    {editingId ? "Update Income" : "Add Income"}
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
                placeholder="Search Income..."
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

                        <th>Source</th>
                        <th>Amount</th>
                        <th>Date</th>
                        <th>Description</th>
                        <th>Action</th>

                    </tr>

                </thead>

                <tbody>

                    {incomeList
                        .filter((income) =>
                            income.source
                                .toLowerCase()
                                .includes(search.toLowerCase())
                        )
                        .map((income) => (

                            <tr key={income.id}>

                                <td>{income.source}</td>

                                <td>₹ {income.amount}</td>

                                <td>{income.date}</td>

                                <td>{income.description}</td>

                                <td>

                                    <button
                                        onClick={() => editIncome(income)}
                                    >
                                        Edit
                                    </button>

                                    <button
                                        onClick={() => deleteIncome(income.id)}
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

export default Income;