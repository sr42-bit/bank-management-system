import { useEffect, useState, useCallback } from "react";
import API from "../../services/api/api";
import { useNavigate } from "react-router-dom";

const EMPTY_FORM = {
  firstName: "",
  lastName: "",
  email: "",
  phone: "",
  gender: "MALE",
  dob: ""
};

function Customers() {
  const navigate = useNavigate();
  const [customers, setCustomers] = useState([]);
  const [search, setSearch] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [editingCustomer, setEditingCustomer] = useState(null);
  const [form, setForm] = useState(EMPTY_FORM);

  // ================= LOAD =================
  const loadCustomers = useCallback(async () => {
    try {
      const res = await API.get("/customers");
      setCustomers(res.data.data || []);
    } catch (err) {
      console.error("Load error:", err);
    }
  }, []);

  useEffect(() => {
    loadCustomers();
  }, [loadCustomers]);

  // ================= FORM =================
  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  // ================= SAVE =================
  const saveCustomer = async () => {
    try {
      if (!form.firstName || !form.lastName || !form.email) {
        alert("Fill all required fields");
        return;
      }

      if (editingCustomer) {
        await API.put(`/customers/${editingCustomer.customerId}`, form);
      } else {
        await API.post("/customers", form);
      }

      alert("Saved successfully ✅");
      setShowModal(false);
      loadCustomers();

    } catch (err) {
      console.error("Save error:", err);
      alert(err.response?.data?.message || "Save failed ❌");
    }
  };

  // ================= DELETE =================
  const deleteCustomer = async (id) => {
    try {
      await API.delete(`/customers/${id}`);
      loadCustomers();
    } catch (err) {
      console.error("Delete error:", err);
    }
  };

  return (
    <div style={styles.container}>

      {/* HEADER */}
      <div style={styles.header}>
        <h2>Customers</h2>

        <button
          style={styles.addBtn}
          onClick={() => {
            setForm(EMPTY_FORM);
            setEditingCustomer(null);
            setShowModal(true);
          }}
        >
          + Add Customer
        </button>
      </div>

      {/* SEARCH */}
      <input
        style={styles.search}
        placeholder="Search customers..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
      />

      {/* TABLE */}
      <div style={styles.tableBox}>
        <table style={styles.table}>
          
          {/* ✅ BLACK HEADER */}
          <thead style={styles.thead}>
            <tr>
              <th style={styles.th}>ID</th>
              <th style={styles.th}>Name</th>
              <th style={styles.th}>Email</th>
              <th style={styles.th}>Phone</th>
              <th style={styles.th}>Actions</th>
            </tr>
          </thead>

          <tbody>
            {customers
              .filter((c) =>
                (c.name || `${c.firstName || ""} ${c.lastName || ""}`)
                  .toLowerCase()
                  .includes(search.toLowerCase())
              )
              .map((c, i) => (
                <tr key={c.customerId} style={i % 2 === 0 ? styles.row : styles.altRow}>

                  <td style={styles.td}>{c.customerId}</td>

                  <td style={styles.td}>
                    {c.name ||
                      `${c.firstName || ""} ${c.lastName || ""}`.trim() ||
                      "N/A"}
                  </td>

                  <td style={styles.td}>{c.email}</td>
                  <td style={styles.td}>{c.phone}</td>

                  <td style={styles.td}>
                    <div style={styles.actions}>

                      <button
                        style={styles.view}
                        onClick={() => navigate(`/admin/customers/${c.customerId}`)}
                      >
                        View
                      </button>

                      <button
                        style={styles.edit}
                        onClick={() => {
                          setEditingCustomer(c);

                          const parts = (c.name || "").trim().split(" ");
                          const first = parts[0] || "";
                          const last = parts.slice(1).join(" ") || "";

                          setForm({
                            firstName: c.firstName || first,
                            lastName: c.lastName || last,
                            email: c.email || "",
                            phone: c.phone || "",
                            gender: c.gender || "MALE",
                            dob: c.dob ? c.dob.split("T")[0] : ""
                          });

                          setShowModal(true);
                        }}
                      >
                        Edit
                      </button>

                      <button
                        style={styles.delete}
                        onClick={() => deleteCustomer(c.customerId)}
                      >
                        Delete
                      </button>

                    </div>
                  </td>

                </tr>
              ))}
          </tbody>
        </table>
      </div>

      {/* MODAL */}
      {showModal && (
        <div style={styles.overlay}>
          <div style={styles.modal}>

            <h3 style={styles.modalTitle}>
              {editingCustomer ? "Edit Customer" : "Add Customer"}
            </h3>

            <div style={styles.form}>
              <input style={styles.input} name="firstName" placeholder="First Name" value={form.firstName} onChange={handleChange} />
              <input style={styles.input} name="lastName" placeholder="Last Name" value={form.lastName} onChange={handleChange} />
              <input style={styles.input} name="email" placeholder="Email" value={form.email} onChange={handleChange} />
              <input style={styles.input} name="phone" placeholder="Phone" value={form.phone} onChange={handleChange} />

              <select style={styles.input} name="gender" value={form.gender} onChange={handleChange}>
                <option>MALE</option>
                <option>FEMALE</option>
                <option>OTHER</option>
              </select>

              <input style={styles.input} type="date" name="dob" value={form.dob} onChange={handleChange} />
            </div>

            <div style={styles.modalBtns}>
              <button style={styles.cancel} onClick={() => setShowModal(false)}>Cancel</button>
              <button style={styles.save} onClick={saveCustomer}>Save</button>
            </div>

          </div>
        </div>
      )}
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

  addBtn: {
    background: "#2563eb",
    color: "white",
    padding: "10px 15px",
    border: "none",
    borderRadius: "6px"
  },

  search: {
    width: "100%",
    padding: "10px",
    marginBottom: "20px",
    borderRadius: "6px",
    border: "1px solid #ddd"
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

  actions: {
    display: "flex",
    justifyContent: "flex-start",
    gap: "8px"
  },

  view: {
    background: "#22c55e",
    color: "white",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  },

  edit: {
    background: "#f59e0b",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  },

  delete: {
    background: "#ef4444",
    color: "white",
    border: "none",
    padding: "6px 10px",
    borderRadius: "6px"
  },

  overlay: {
    position: "fixed",
    inset: 0,
    background: "rgba(0,0,0,0.5)",
    display: "flex",
    justifyContent: "center",
    alignItems: "center"
  },

  modal: {
    background: "white",
    padding: "30px",
    borderRadius: "14px",
    width: "420px",
    boxShadow: "0 20px 50px rgba(0,0,0,0.2)"
  },

  modalTitle: {
    fontSize: "20px",
    fontWeight: "600",
    marginBottom: "10px"
  },

  form: {
    display: "grid",
    gap: "12px"
  },

  input: {
    padding: "12px",
    borderRadius: "8px",
    border: "1px solid #e5e7eb",
    fontSize: "14px"
  },

  modalBtns: {
    display: "flex",
    justifyContent: "flex-end",
    gap: "10px",
    marginTop: "15px"
  },

  cancel: {
    background: "#6b7280",
    color: "white",
    border: "none",
    padding: "8px 12px",
    borderRadius: "6px"
  },

  save: {
    background: "#2563eb",
    color: "white",
    border: "none",
    padding: "8px 12px",
    borderRadius: "6px"
  }
};

export default Customers;