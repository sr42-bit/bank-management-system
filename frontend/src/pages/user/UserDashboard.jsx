import { useNavigate } from "react-router-dom";

function UserDashboard() {
  const navigate = useNavigate();

  const cards = [
    {
      title: "My Accounts",
      desc: "Check balances and account details",
      btn: "View Accounts",
      color: "#2563eb",
      path: "/user/accounts"
    },
    {
      title: "Deposit",
      desc: "Add money securely to your account",
      btn: "Deposit",
      color: "#22c55e",
      path: "/user/deposit"
    },
    {
      title: "Withdraw",
      desc: "Withdraw money instantly",
      btn: "Withdraw",
      color: "#f59e0b",
      path: "/user/withdraw"
    },
    {
      title: "Transactions",
      desc: "Track your recent activity",
      btn: "View History",
      color: "#111827",
      path: "/user/transactions"
    }
  ];

  return (
    <div style={styles.page}>

      {/* HEADER */}
      <div style={styles.header}>
        <h1 style={styles.heading}>Dashboard</h1>
        <p style={styles.subheading}>Welcome back 👋</p>
      </div>

      {/* CARDS */}
      <div style={styles.grid}>
        {cards.map((card, i) => (
          <div key={i} style={styles.card}>
            
            <div style={styles.cardContent}>
              <h3 style={styles.cardTitle}>{card.title}</h3>
              <p style={styles.cardDesc}>{card.desc}</p>
            </div>

            <button
              style={{ ...styles.button, background: card.color }}
              onClick={() => navigate(card.path)}
            >
              {card.btn}
            </button>

          </div>
        ))}
      </div>

    </div>
  );
}

const styles = {
  page: {
    padding: "40px",
    background: "#f8fafc",
    minHeight: "100vh"
  },

  header: {
    marginBottom: "30px"
  },

  heading: {
    fontSize: "32px",
    fontWeight: "700",
    marginBottom: "5px"
  },

  subheading: {
    color: "#6b7280",
    fontSize: "15px"
  },

  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(260px, 1fr))",
    gap: "20px"
  },

  card: {
    background: "white",
    borderRadius: "16px",
    padding: "20px",
    display: "flex",
    flexDirection: "column",
    justifyContent: "space-between",
    height: "180px",
    boxShadow: "0 6px 18px rgba(0,0,0,0.06)",
    transition: "all 0.2s ease",
    cursor: "pointer"
  },

  cardContent: {
    display: "flex",
    flexDirection: "column",
    gap: "6px"
  },

  cardTitle: {
    fontSize: "18px",
    fontWeight: "600"
  },

  cardDesc: {
    fontSize: "14px",
    color: "#6b7280"
  },

  button: {
    border: "none",
    color: "white",
    padding: "10px",
    borderRadius: "8px",
    fontWeight: "500",
    cursor: "pointer"
  }
};

export default UserDashboard;