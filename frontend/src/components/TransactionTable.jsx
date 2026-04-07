import { useState } from "react";
import api from "../api/api";

function TransactionTable() {
  const [accountId, setAccountId] = useState("");
  const [transactions, setTransactions] = useState([]);

  const fetchTransactions = async () => {
    if (!accountId) {
      alert("Enter Account ID");
      return;
    }

    try {
      const response = await api.get(`/transactions?accountId=${accountId}`);
      setTransactions(response.data);
    } catch (error) {
      console.error(error);
      alert("Failed to fetch transactions");
    }
  };

  return (
    <div className="card p-4">
      <h4 className="mb-3">Transaction History</h4>

      <div className="mb-3">
        <input
          type="text"
          className="form-control"
          placeholder="Enter Account ID"
          value={accountId}
          onChange={(e) => setAccountId(e.target.value)}
        />
      </div>

      <button className="btn btn-primary mb-3" onClick={fetchTransactions}>
        Load Transactions
      </button>

      <table className="table table-bordered">
        <thead className="table-dark">
          <tr>
            <th>Type</th>
            <th>Amount</th>
            <th>From</th>
            <th>To</th>
          </tr>
        </thead>
        <tbody>
          {transactions.length === 0 ? (
            <tr>
              <td colSpan="4" className="text-center">
                No Transactions
              </td>
            </tr>
          ) : (
            transactions.map((txn, index) => (
              <tr key={index}>
                <td>{txn.type}</td>
                <td>{txn.amount}</td>
                <td>{txn.fromAccountId}</td>
                <td>{txn.toAccountId}</td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
}

export default TransactionTable;