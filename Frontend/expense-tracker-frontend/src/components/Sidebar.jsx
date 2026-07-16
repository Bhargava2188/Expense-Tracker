import { Link, useNavigate } from "react-router-dom";

function Sidebar() {

    const navigate = useNavigate();

    const handleLogout = () => {

        localStorage.removeItem("token");

        navigate("/login");

    };

    return (

        <div
            style={{
                width: "220px",
                background: "#222",
                minHeight: "100vh",
                color: "white",
                padding: "20px"
            }}
        >

            <h2>Expense Tracker</h2>

            <hr />

            <br />

            <Link
                to="/dashboard"
                style={{ color: "white", textDecoration: "none" }}
            >
                🏠 Dashboard
            </Link>

            <br /><br />

            <Link
                to="/expenses"
                style={{ color: "white", textDecoration: "none" }}
            >
                💸 Expenses
            </Link>

            <br /><br />

            <Link
                to="/income"
                style={{ color: "white", textDecoration: "none" }}
            >
                💰 Income
            </Link>

            <br /><br />

            <Link
                to="/profile"
                style={{ color: "white", textDecoration: "none" }}
            >
                👤 Profile
            </Link>

            <br /><br />

            <button
                onClick={handleLogout}
                style={{
                    width: "100%",
                    padding: "10px",
                    background: "#f44336",
                    color: "white",
                    border: "none",
                    cursor: "pointer",
                    borderRadius: "5px"
                }}
            >
                🚪 Logout
            </button>

        </div>

    );
}

export default Sidebar;