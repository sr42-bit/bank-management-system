import { useState } from "react";
import api from "../../services/api/api";

function Transactions() {

  const [accountId, setAccountId] = useState("");
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const fetchTransactions = async () => {
    if (!accountId.trim()) {
      setError("Enter a valid Account ID");
      return;
    }

    setLoading(true);
    setError("");

    try {
      const res = await api.get(`/transactions?accountId=${accountId}`);
      setTransactions(res.data);
    } catch (error) {
      console.error(error);
      setError("Failed to fetch transactions");
    } finally {
      setLoading(false);
    }
  };

  const getBadge = (type) => {
    const badges = {
      DEPOSIT: "bg-success",
      WITHDRAW: "bg-danger",
      TRANSFER: "bg-info"
    };
    return badges[type] || "bg-secondary";
  };

  const handleKeyPress = (e) => {
    if (e.key === "Enter") fetchTransactions();
  };

  return (
    <div className="container-fluid py-4" style={{ backgroundColor: "#f8f9fa", minHeight: "100vh" }}>
      <div className="row mb-4">
        <div className="col-lg-10 mx-auto">
          <h2 className="fw-bold text-dark mb-1">Transaction History</h2>
          <p className="text-muted">View all transactions for your account</p>
        </div>
      </div>

      <div className="row">
        <div className="col-lg-10 mx-auto">
          <div className="card shadow-sm border-0 rounded-3">
            <div className="card-body p-4">
              {/* Search Bar */}
              <div className="row g-3 mb-4">
                <div className="col-md-9">
                  <input
                    type="text"
                    className="form-control form-control-lg rounded-2"
                    placeholder="🔍 Enter Account ID"
                    value={accountId}
                    onChange={(e) => setAccountId(e.target.value)}
                    onKeyPress={handleKeyPress}
                  />
                </div>
                <div className="col-md-3">
                  <button
                    className="btn btn-primary btn-lg w-100 rounded-2"
                    onClick={fetchTransactions}
                    disabled={loading}
                  >
                    {loading ? "Loading..." : "Search"}
                  </button>
                </div>
              </div>

              {/* Error Message */}
              {error && (
                <div className="alert alert-danger alert-dismissible fade show rounded-2" role="alert">
                  {error}
                  <button type="button" className="btn-close" onClick={() => setError("")}></button>
                </div>
              )}

              {/* Table */}
              <div className="table-responsive">
                <table className="table table-hover mb-0">
                  <thead>
                    <tr className="border-bottom-2 fw-bold text-muted text-uppercase" style={{ fontSize: "0.85rem" }}>
                      <th className="ps-0">Transaction ID</th>
                      <th>Type</th>
                      <th>Amount</th>
                      <th>From Account</th>
                      <th>To Account</th>
                      <th>Date & Time</th>
                    </tr>
                  </thead>
                  <tbody>
                    {transactions.length === 0 ? (
                      <tr>
                        <td colSpan="6" className="text-center py-5 text-muted">
                          <p className="mb-0">{loading ? "Loading transactions..." : "No transactions found"}</p>
                        </td>
                      </tr>
                    ) : (
                      transactions.map(txn => (
                        <tr key={txn.transactionId} className="border-bottom">
                          <td className="fw-semibold ps-0">{txn.transactionId}</td>
                          <td>
                            <span className={`badge ${getBadge(txn.type)} rounded-pill`}>
                              {txn.type}
                            </span>
                          </td>
                          <td className="fw-bold text-success">₹ {txn.amount.toLocaleString("en-IN")}</td>
                          <td className="text-muted">{txn.fromAccountId || "—"}</td>
                          <td className="text-muted">{txn.toAccountId || "—"}</td>
                          <td className="text-muted" style={{ fontSize: "0.9rem" }}>
                            {new Date(txn.createdAt).toLocaleString("en-IN")}
                          </td>
                        </tr>
                      ))
                    )}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Transactions;
