import { useNavigate } from "react-router-dom";

function Navbar() {

    const navigate = useNavigate();

    const logout = () => {

        localStorage.removeItem("token");

        navigate("/");

    };

    return (

        <div
            style={{
                display: "flex",
                justifyContent: "space-between",
                background: "#1976d2",
                color: "white",
                padding: "15px"
            }}
        >

            <h2>Expense Tracker</h2>

            <button onClick={logout}>
                Logout
            </button>

        </div>

    );
}

export default Navbar;