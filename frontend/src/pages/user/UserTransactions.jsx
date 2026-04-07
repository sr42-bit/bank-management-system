import { useEffect, useState } from "react";
import api from "../../services/api/api";

function UserTransactions() {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchTransactions = async () => {
      try {
        const res = await api.get("/user/transactions");
        setTransactions(res.data || []);
      } catch (err) {
        console.error(err);
        alert("Failed to load transactions");
      } finally {
        setLoading(false);
      }
    };

    fetchTransactions();
  }, []);

  return (
    <div style={styles.page}>
      <h2 style={styles.title}>Transaction History</h2>

      {loading && <p>Loading...</p>}

      {!loading && transactions.length === 0 && (
        <p style={styles.empty}>No transactions found</p>
      )}

      <div style={styles.list}>
        {transactions.map((tx) => {
          const isCredit = tx.type === "DEPOSIT" || tx.toAccountId;

          return (
            <div key={tx.transactionId} style={styles.card}>
              <div style={styles.row}>
                <span style={styles.type}>{tx.type}</span>

                <span
                  style={{
                    ...styles.amount,
                    color: isCredit ? "#16a34a" : "#ef4444",
                  }}
                >
                  {isCredit ? "+" : "-"} ₹ {tx.amount}
                </span>
              </div>

              <div style={styles.rowSmall}>
                <span>
                  From: {tx.fromAccountId || "—"}
                </span>
                <span>
                  To: {tx.toAccountId || "—"}
                </span>
              </div>

              <div style={styles.rowSmall}>
                <span>Status: {tx.status}</span>
                <span>
                  {new Date(tx.createdAt).toLocaleString()}
                </span>
              </div>
            </div>
          );
        })}
      </div>
    </div>
  );
}

const styles = {
  page: {
    padding: "30px",
    background: "#f8fafc",
    minHeight: "100vh",
  },

  title: {
    fontSize: "26px",
    marginBottom: "20px",
  },

  list: {
    display: "flex",
    flexDirection: "column",
    gap: "15px",
  },

  card: {
    background: "white",
    padding: "16px",
    borderRadius: "12px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.05)",
    display: "flex",
    flexDirection: "column",
    gap: "8px",
  },

  row: {
    display: "flex",
    justifyContent: "space-between",
    fontWeight: "600",
  },

  rowSmall: {
    display: "flex",
    justifyContent: "space-between",
    fontSize: "13px",
    color: "#6b7280",
  },

  type: {
    fontSize: "14px",
  },

  amount: {
    fontSize: "16px",
    fontWeight: "700",
  },

  empty: {
    color: "#6b7280",
  },
};

export default UserTransactions;