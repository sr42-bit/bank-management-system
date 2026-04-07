import { useEffect, useState } from "react";
import api from "../../services/api/api";

function Accounts() {
  const [accounts, setAccounts] = useState([]);
  const [customerId, setCustomerId] = useState("");
  const [initialBalance, setInitialBalance] = useState("");
  const [loading, setLoading] = useState(false);

  const loadAccounts = async () => {
    try {
      const res = await api.get("/admin/accounts");
      setAccounts(res.data);
    } catch (err) {
      console.error("Failed to load accounts", err);
    }
  };

  useEffect(() => {
    loadAccounts();
  }, []);

  // ================= CREATE =================
  const createAccount = async () => {
    if (!customerId.trim() || !initialBalance) {
      alert("Enter valid details");
      return;
    }

    try {
      setLoading(true);

      await api.post("/admin/accounts", {
        customerId: customerId.trim(),
        accountType: "SAVINGS",
        initialDeposit: Number(initialBalance)
      });

      setCustomerId("");
      setInitialBalance("");
      loadAccounts();

    } catch (err) {
      console.error(err);
      alert("Failed to create account");
    } finally {
      setLoading(false);
    }
  };

  // ================= ACTIONS =================
  const deposit = async (id) => {
    const amount = prompt("Enter amount");
    if (!amount || Number(amount) <= 0) return;

    try {
      await api.put(`/accounts/${id}/deposit`, {
        amount: Number(amount)
      });

      alert("Deposit successful ✅");
      loadAccounts();
    } catch (err) {
      alert("Deposit failed ❌");
    }
  };

  const withdraw = async (id) => {
    const amount = prompt("Enter amount");
    if (!amount || Number(amount) <= 0) return;

    try {
      await api.put(`/accounts/${id}/withdraw`, {
        amount: Number(amount)
      });

      alert("Withdraw successful ✅");
      loadAccounts();
    } catch (err) {
      alert("Withdraw failed ❌");
    }
  };

  const closeAcc = async (id, customerId) => {
    try {
      await api.put(`/accounts/${id}/close`, null, {
        params: { customerId }
      });

      alert("Account closed ✅");
      loadAccounts();
    } catch (err) {
      alert("Close failed (balance must be zero) ❌");
    }
  };

  return (
    <div style={styles.container}>

      {/* HEADER */}
      <div style={styles.header}>
        <h2>Accounts</h2>

        <div style={styles.form}>
          <input
            style={styles.input}
            placeholder="Customer ID"
            value={customerId}
            onChange={(e) => setCustomerId(e.target.value)}
            disabled={loading}
          />

          <input
            style={styles.input}
            placeholder="Initial Balance"
            type="number"
            value={initialBalance}
            onChange={(e) => setInitialBalance(e.target.value)}
            disabled={loading}
          />

          <button
            style={styles.addBtn}
            onClick={createAccount}
            disabled={loading}
          >
            {loading ? "Opening..." : "+ Open"}
          </button>
        </div>
      </div>

      {/* TABLE */}
      <div style={styles.tableBox}>
        <table style={styles.table}>

          {/* ✅ BLACK HEADER */}
          <thead style={styles.thead}>
            <tr>
              <th style={styles.th}>ID</th>
              <th style={styles.th}>Customer</th>
              <th style={styles.th}>Balance</th>
              <th style={styles.th}>Status</th>
              <th style={styles.th}>Actions</th>
            </tr>
          </thead>

          <tbody>
            {accounts.map((acc, i) => (
              <tr key={acc.accountId} style={i % 2 === 0 ? styles.row : styles.altRow}>

                <td style={styles.td}>{acc.accountId}</td>
                <td style={styles.td}>{acc.customerId}</td>

                <td style={{ ...styles.td, color: "#16a34a", fontWeight: "600" }}>
                  ₹ {acc.balance}
                </td>

                <td style={styles.td}>
                  <span style={{
                    ...styles.badge,
                    background: acc.status === "ACTIVE" ? "#dcfce7" : "#e2e8f0",
                    color: acc.status === "ACTIVE" ? "#16a34a" : "#64748b"
                  }}>
                    {acc.status}
                  </span>
                </td>

                <td style={styles.td}>
                  {acc.status === "ACTIVE" && (
                    <div style={styles.actions}>
                      <button style={styles.deposit}
                        onClick={() => deposit(acc.accountId)}>
                        Deposit
                      </button>

                      <button style={styles.withdraw}
                        onClick={() => withdraw(acc.accountId)}>
                        Withdraw
                      </button>

                      <button style={styles.close}
                        onClick={() => closeAcc(acc.accountId, acc.customerId)}>
                        Close
                      </button>
                    </div>
                  )}
                </td>

              </tr>
            ))}
          </tbody>

        </table>
      </div>

    </div>
  );
}

// ================= STYLES =================
const styles = {
  container: { padding: "25px" },

  header: {
    display: "flex",
    justifyContent: "space-between",
    marginBottom: "20px"
  },

  form: { display: "flex", gap: "10px" },

  input: {
    padding: "10px",
    borderRadius: "6px",
    border: "1px solid #ddd"
  },

  addBtn: {
    background: "#2563eb",
    color: "white",
    border: "none",
    padding: "10px 15px",
    borderRadius: "6px"
  },

  tableBox: {
    background: "white",
    borderRadius: "10px",
    overflow: "hidden",
    boxShadow: "0 4px 12px rgba(0,0,0,0.05)"
  },

  table: {
    width: "100%",
    borderCollapse: "collapse"
  },

  thead: {
    background: "#111827",
    color: "white"
  },

  th: {
    padding: "14px",
    fontWeight: "600"
  },

  td: {
    padding: "12px",
    borderBottom: "1px solid #f1f5f9"
  },

  row: { background: "#fff" },
  altRow: { background: "#f9fafb" },

  badge: {
    padding: "4px 10px",
    borderRadius: "20px",
    fontSize: "12px"
  },

  actions: {
    display: "flex",
    justifyContent: "flex-start",
    gap: "8px"
  },

  deposit: {
    background: "#22c55e",
    color: "white",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  },

  withdraw: {
    background: "#f59e0b",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  },

  close: {
    background: "#ef4444",
    color: "white",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  }
};

export default Accounts;