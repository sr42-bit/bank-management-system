import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import API from "../../services/api/api";

function CustomerDetails() {
  const { id } = useParams();

  const [customer, setCustomer] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    if (!id) {
      setError("Invalid customer ID");
      setLoading(false);
      return;
    }

    const fetchCustomer = async () => {
      try {
        const res = await API.get(`/admin/customers/${id}`);
        console.log("Customer API:", res.data); // ✅ DEBUG
        setCustomer(res.data);
      } catch (err) {
        console.error("Fetch error:", err);
        setError("Failed to load customer");
      } finally {
        setLoading(false);
      }
    };

    fetchCustomer();
  }, [id]);

  if (loading) return <div style={styles.center}>Loading...</div>;
  if (error) return <div style={styles.error}>{error}</div>;
  if (!customer) return <div style={styles.warn}>Customer not found</div>;

  return (
    <div style={styles.container}>
      <h2 style={styles.title}>Customer Details</h2>

      <div style={styles.tableBox}>
        <table style={styles.table}>
          <tbody>

            <tr>
              <td style={styles.label}>Customer ID</td>
              <td style={styles.value}>{customer.customerId}</td>
            </tr>

            {/* ✅ FIXED NAME */}
            <tr>
              <td style={styles.label}>Name</td>
              <td style={styles.value}>
                {customer.name || "N/A"}
              </td>
            </tr>

            <tr>
              <td style={styles.label}>Email</td>
              <td style={styles.value}>{customer.email}</td>
            </tr>

            <tr>
              <td style={styles.label}>Phone</td>
              <td style={styles.value}>{customer.phone}</td>
            </tr>

            <tr>
              <td style={styles.label}>Gender</td>
              <td style={styles.value}>{customer.gender}</td>
            </tr>

            <tr>
              <td style={styles.label}>DOB</td>
              <td style={styles.value}>
                {customer.dob
                  ? new Date(customer.dob).toLocaleDateString()
                  : "N/A"}
              </td>
            </tr>

            <tr>
              <td style={styles.label}>Status</td>
              <td style={styles.value}>
                <span
                  style={{
                    ...styles.badge,
                    background: "#dcfce7",
                    color: "#16a34a",
                  }}
                >
                  {customer.status || "ACTIVE"}
                </span>
              </td>
            </tr>

          </tbody>
        </table>
      </div>
    </div>
  );
}

const styles = {
  container: { padding: "25px" },
  title: { fontSize: "26px", marginBottom: "20px" },
  tableBox: {
    background: "white",
    borderRadius: "10px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.05)",
    overflow: "hidden",
    maxWidth: "700px"
  },
  table: { width: "100%", borderCollapse: "collapse" },
  label: {
    background: "#111827",
    color: "white",
    padding: "14px",
    width: "35%",
    fontWeight: "600"
  },
  value: {
    padding: "14px",
    borderBottom: "1px solid #e5e7eb"
  },
  badge: {
    padding: "4px 10px",
    borderRadius: "20px",
    fontSize: "12px"
  },
  center: { padding: "40px", textAlign: "center" },
  error: { color: "red", padding: "20px" },
  warn: { color: "orange", padding: "20px" }
};

export default CustomerDetails;