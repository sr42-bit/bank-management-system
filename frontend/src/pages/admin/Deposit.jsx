import { useState } from "react";
import api from "../../services/api/api";

function Deposit() {
  const [accountId, setAccountId] = useState("");
  const [amount, setAmount] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: "", text: "" });

  const handleDeposit = async () => {
    const accId = accountId.trim();
    const amt = Number(amount);

    setMessage({ type: "", text: "" });

    // ✅ Validation
    if (!accId) {
      setMessage({ type: "error", text: "Account ID is required" });
      return;
    }

    if (!amt || amt <= 0) {
      setMessage({ type: "error", text: "Enter a valid amount" });
      return;
    }

    try {
      setLoading(true);

      const res = await api.put(
        `/admin/accounts/${accId}/deposit`,
        { amount: amt }
      );

      const balance = res.data?.balance ?? "Updated";

      setMessage({
        type: "success",
        text: `Deposit successful ✅ New Balance: ₹ ${balance}`
      });

      // reset
      setAccountId("");
      setAmount("");

    } catch (err) {
      console.error(err);

      const errorMsg =
        err.response?.data?.message ||
        err.response?.data ||
        "Deposit failed";

      setMessage({ type: "error", text: errorMsg });

    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.container}>

      <h2 style={styles.title}>Deposit Money</h2>

      <div style={styles.card}>

        {/* ✅ MESSAGE */}
        {message.text && (
          <div
            style={{
              ...styles.message,
              background:
                message.type === "success" ? "#dcfce7" : "#fee2e2",
              color:
                message.type === "success" ? "#166534" : "#991b1b"
            }}
          >
            {message.text}
          </div>
        )}

        <input
          style={styles.input}
          placeholder="Account ID"
          value={accountId}
          disabled={loading}
          onChange={(e) => setAccountId(e.target.value)}
        />

        <input
          style={styles.input}
          type="number"
          placeholder="Amount"
          value={amount}
          disabled={loading}
          onChange={(e) => setAmount(e.target.value)}
        />

        <button
          style={{
            ...styles.button,
            opacity: loading ? 0.7 : 1,
            cursor: loading ? "not-allowed" : "pointer"
          }}
          onClick={handleDeposit}
          disabled={loading}
        >
          {loading ? "Processing..." : "Deposit"}
        </button>

      </div>

    </div>
  );
}

const styles = {
  container: {
    padding: "25px"
  },

  title: {
    fontSize: "24px",
    marginBottom: "20px"
  },

  card: {
    background: "white",
    padding: "20px",
    borderRadius: "10px",
    maxWidth: "400px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.05)",
    display: "flex",
    flexDirection: "column",
    gap: "12px"
  },

  input: {
    padding: "10px",
    borderRadius: "6px",
    border: "1px solid #ddd",
    fontSize: "14px"
  },

  button: {
    background: "#22c55e",
    color: "white",
    border: "none",
    padding: "10px",
    borderRadius: "6px",
    fontWeight: "600",
    transition: "0.2s"
  },

  message: {
    padding: "10px",
    borderRadius: "6px",
    fontSize: "14px"
  }
};

export default Deposit;