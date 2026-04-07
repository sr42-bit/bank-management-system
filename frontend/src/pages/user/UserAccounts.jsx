import { useEffect, useState } from "react";
import api from "../../services/api/api";

function UserAccounts() {
  const [accounts, setAccounts] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        const token = localStorage.getItem("token");

        if (!token) {
          setError("Session expired. Please login again.");
          setLoading(false);
          return;
        }

        const res = await api.get("/user/accounts");

        console.log("✅ Accounts response:", res.data);

        // ✅ HANDLE BOTH RESPONSE TYPES
        const data = Array.isArray(res.data)
          ? res.data
          : res.data?.data || [];

        setAccounts(data);

      } catch (err) {
        console.error("❌ Full Error:", err);
        console.error("❌ Response:", err.response);

        if (err.response?.status === 403) {
          setError("Unauthorized (403). Please login again.");
        } else if (err.response?.status === 404) {
          setError("No accounts found.");
        } else {
          setError(err.response?.data?.message || "Failed to load accounts.");
        }
      } finally {
        setLoading(false);
      }
    };

    fetchAccounts();
  }, []);

  return (
    <div style={styles.page}>
      <h2 style={styles.heading}>My Accounts</h2>

      {loading && <p>Loading...</p>}

      {!loading && error && <p style={{ color: "red" }}>{error}</p>}

      {!loading && !error && accounts.length === 0 && (
        <p style={styles.empty}>No accounts found</p>
      )}

      <div style={styles.grid}>
        {accounts.map((acc) => (
          <div key={acc.accountId} style={styles.card}>
            <div style={styles.top}>
              <p style={styles.accountId}>{acc.accountId}</p>

              <span
                style={{
                  ...styles.status,
                  background:
                    acc.status === "ACTIVE" ? "#dcfce7" : "#e2e8f0",
                  color:
                    acc.status === "ACTIVE" ? "#16a34a" : "#64748b",
                }}
              >
                {acc.status}
              </span>
            </div>

            <div style={styles.balanceBox}>
              <p style={styles.balanceLabel}>Balance</p>
              <h3 style={styles.balance}>
                ₹ {acc.balance ?? 0}
              </h3>
            </div>
          </div>
        ))}
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
  heading: {
    fontSize: "26px",
    fontWeight: "700",
    marginBottom: "20px",
  },
  grid: {
    display: "grid",
    gridTemplateColumns: "repeat(auto-fit, minmax(280px, 1fr))",
    gap: "20px",
  },
  card: {
    background: "white",
    borderRadius: "16px",
    padding: "20px",
    boxShadow: "0 6px 18px rgba(0,0,0,0.05)",
    display: "flex",
    flexDirection: "column",
    gap: "20px",
  },
  top: {
    display: "flex",
    justifyContent: "space-between",
    alignItems: "center",
  },
  accountId: {
    fontSize: "13px",
    color: "#6b7280",
  },
  status: {
    padding: "4px 10px",
    borderRadius: "20px",
    fontSize: "12px",
    fontWeight: "500",
  },
  balanceBox: {
    display: "flex",
    flexDirection: "column",
    gap: "5px",
  },
  balanceLabel: {
    fontSize: "13px",
    color: "#6b7280",
  },
  balance: {
    fontSize: "22px",
    fontWeight: "700",
    color: "#16a34a",
  },
  empty: {
    color: "#6b7280",
  },
};

export default UserAccounts;