import { useState } from "react";
import API from "../../services/api/api";

function CloseAccount() {
  const [accountId, setAccountId] = useState("");
  const [customerId, setCustomerId] = useState("");
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState({ type: "", text: "" });

  const closeAccount = async () => {
    const accId = accountId.trim();
    const custId = customerId.trim();

    if (!accId || !custId) {
      setMessage({ type: "error", text: "Both Account ID and Customer ID are required" });
      return;
    }

    setLoading(true);
    setMessage({ type: "", text: "" });

    try {
      await API.put(
        `/admin/accounts/${accId}/close`,
        null,
        { params: { customerId: custId } }
      );

      setMessage({ type: "success", text: "Account closed successfully ✅" });

      // reset fields
      setAccountId("");
      setCustomerId("");

    } catch (err) {
      console.error(err);

      const errorMsg =
        err.response?.data?.message ||
        err.response?.data ||
        "Failed to close account";

      setMessage({ type: "error", text: errorMsg });
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Close Account</h2>

      <div style={styles.card}>

        {/* MESSAGE */}
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
          placeholder="Customer ID"
          value={customerId}
          disabled={loading}
          onChange={(e) => setCustomerId(e.target.value)}
        />

        <button
          style={{
            ...styles.button,
            opacity: loading ? 0.7 : 1,
            cursor: loading ? "not-allowed" : "pointer"
          }}
          onClick={closeAccount}
          disabled={loading}
        >
          {loading ? "Closing Account..." : "Close Account"}
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
    background: "#ef4444",
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

export default CloseAccount;