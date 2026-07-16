function SummaryCard({ title, value, color }) {

    return (

        <div
            style={{
                background: color,
                color: "white",
                padding: "20px",
                borderRadius: "10px",
                width: "220px",
                textAlign: "center",
                boxShadow: "0 2px 8px rgba(0,0,0,0.2)"
            }}
        >

            <h3>{title}</h3>

            <h2>₹ {value}</h2>

        </div>

    );

}

export default SummaryCard;