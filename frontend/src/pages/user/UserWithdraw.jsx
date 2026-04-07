import { useEffect, useState } from "react";
import api from "../../services/api/api";

function UserWithdraw() {
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState("");
  const [amount, setAmount] = useState("");
  const [balance, setBalance] = useState(null);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchAccounts = async () => {
      try {
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

  const handleWithdraw = async () => {
    if (!selectedAccount || !amount) {
      alert("Fill all fields");
      return;
    }

    if (Number(amount) <= 0) {
      alert("Invalid amount");
      return;
    }

    if (balance !== null && Number(amount) > balance) {
      alert("Insufficient balance ❌");
      return;
    }

    try {
      setLoading(true);

      const res = await api.post("/user/withdraw", {
        accountId: selectedAccount,
        amount: Number(amount)
      });

      alert("Withdraw successful ✅");

      setBalance(res.data.balance);
      setAmount("");

    } catch (err) {
      console.error(err);
      alert("Withdraw failed ❌");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div style={styles.page}>
      <h2 style={styles.title}>Withdraw Money</h2>

      <div style={styles.card}>

        {/* ACCOUNT SELECT */}
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

        {/* BALANCE */}
        {balance !== null && (
          <p style={styles.balance}>Balance: ₹ {balance}</p>
        )}

        {/* AMOUNT */}
        <input
          style={styles.input}
          type="number"
          placeholder="Enter amount"
          value={amount}
          onChange={(e) => setAmount(e.target.value)}
        />

        {/* BUTTON */}
        <button
          style={styles.button}
          onClick={handleWithdraw}
          disabled={loading}
        >
          {loading ? "Processing..." : "Withdraw"}
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
    background: "#ef4444", // red for withdraw
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

export default UserWithdraw;