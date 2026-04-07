import { useEffect, useState } from "react";
import api from "../../services/api/api";

function UserDeposit() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [balance, setBalance] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
        // ✅ FIX: no customerId in URL
        const res = await api.get("/user/accounts");

        setAccounts(res.data || []);
      } catch (err) {
        console.error(err);
        alert("Failed to load accounts");
      }
    };

    fetchAccounts();
  }, []);

  const handleAccountChange = (id) => {
    setSelectedAccount(id);

    const acc = accounts.find(a => a.accountId === id);
    setBalance(acc?.balance);
  };

  const handleDeposit = async () => {
    if (!selectedAccount || !amount) {
      alert("Fill all fields");
      return;
    }

    try {
      setLoading(true);

      const res = await api.post("/user/deposit", {
        accountId: selectedAccount,
        amount: Number(amount)
      });

      alert("Deposit successful ✅");

      // ✅ update balance safely
      setBalance(res.data.balance);
      setAmount("");

    } catch (err) {
      console.error(err);
      alert("Deposit failed ❌");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.page}>
      <h2 style={styles.title}>Deposit Money</h2>

      <div style={styles.card}>

        <select
          style={styles.input}
          value={selectedAccount}
          onChange={(e) => handleAccountChange(e.target.value)}
        >
          <option value="">Select Account</option>
          {accounts.map(acc => (
            <option key={acc.accountId} value={acc.accountId}>
              {acc.accountId}
            </option>
          ))}
        </select>

        {balance !== null && (
          <p style={styles.balance}>Balance: ₹ {balance}</p>
        )}

        <input
          style={styles.input}
          type="number"
          placeholder="Enter amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        <button
          style={styles.button}
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
  page: {
    padding: "30px",
    background: "#f8fafc",
    minHeight: "100vh"
  },

  title: {
    fontSize: "26px",
    marginBottom: "20px"
  },

  card: {
    background: "white",
    padding: "25px",
    borderRadius: "12px",
    maxWidth: "400px",
    boxShadow: "0 6px 18px rgba(0,0,0,0.05)",
    display: "flex",
    flexDirection: "column",
    gap: "12px"
  },

  input: {
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid #ddd"
  },

  button: {
    background: "#22c55e",
    color: "white",
    border: "none",
    padding: "12px",
    borderRadius: "8px",
    fontWeight: "600",
    cursor: "pointer"
  },

  balance: {
    color: "#16a34a",
    fontWeight: "600"
  }
};

export default UserDeposit;