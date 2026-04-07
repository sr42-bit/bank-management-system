import { useState } from "react";
import API from "../services/api";

function OpenAccount() {
  const [data, setData] = useState({
    customerId: "",
    type: "",
    deposit: ""
  });
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setData({ ...data, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);

    API.post("/accounts", data)
      .then(() => {
        alert("Account Created Successfully");
        setData({ customerId: "", type: "", deposit: "" });
      })
      .catch(err => console.log(err))
      .finally(() => setLoading(false));
  };

  return (
    <div className="min-vh-100 d-flex align-items-center justify-content-center" style={{ background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", padding: "20px" }}>
      <div className="card shadow-lg border-0" style={{ maxWidth: "450px", width: "100%", borderRadius: "15px" }}>
        <div className="card-body p-5">
          <h2 className="mb-4 text-center" style={{ color: "#333", fontWeight: "600" }}>
            Open New Account
          </h2>

          <form onSubmit={handleSubmit}>
            <div className="mb-4">
              <label className="form-label fw-500" style={{ color: "#555" }}>Customer ID</label>
              <input
                className="form-control"
                name="customerId"
                placeholder="Enter your Customer ID"
                onChange={handleChange}
                value={data.customerId}
                required
                style={{ borderRadius: "8px", border: "1px solid #ddd", padding: "12px" }}
              />
            </div>

            <div className="mb-4">
              <label className="form-label fw-500" style={{ color: "#555" }}>Account Type</label>
              <select
                className="form-control"
                name="type"
                onChange={handleChange}
                value={data.type}
                required
                style={{ borderRadius: "8px", border: "1px solid #ddd", padding: "12px" }}
              >
                <option value="">Select Account Type</option>
                <option value="SAVINGS">SAVINGS</option>
                <option value="CURRENT">CURRENT</option>
              </select>
            </div>

            <div className="mb-4">
              <label className="form-label fw-500" style={{ color: "#555" }}>Initial Deposit</label>
              <input
                className="form-control"
                name="deposit"
                placeholder="Enter initial deposit amount"
                onChange={handleChange}
                value={data.deposit}
                type="number"
                required
                style={{ borderRadius: "8px", border: "1px solid #ddd", padding: "12px" }}
              />
            </div>

            <button
              className="btn w-100 btn-lg fw-600"
              style={{ background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", color: "white", border: "none", borderRadius: "8px", padding: "12px" }}
              disabled={loading}
            >
              {loading ? "Creating..." : "Create Account"}
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}

export default OpenAccount;