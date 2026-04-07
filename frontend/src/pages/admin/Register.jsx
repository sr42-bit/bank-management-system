import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api/api";
import "./Login.css";

function Register() {
  const [form, setForm] = useState({
    name: "",        // UI only
    email: "",
    password: ""
  });

  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");

  const navigate = useNavigate();

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value
    });
  };

  const handleRegister = async () => {
    if (!form.name || !form.email || !form.password) {
      setMessage("All fields are required");
      return;
    }

    if (form.password.length < 6) {
      setMessage("Password must be at least 6 characters");
      return;
    }

    try {
      setLoading(true);

      // 🔥 FIX: send only required fields
      await api.post("/auth/register", {
        email: form.email,
        password: form.password
      });

      setMessage("Registration successful ✅");

      setTimeout(() => {
        navigate("/login");
      }, 1500);

    } catch (err) {
      console.error("REGISTER ERROR:", err);

      if (err.response?.status === 409) {
        setMessage("Email already exists");
      } else if (err.response?.data?.message) {
        setMessage(err.response.data.message);
      } else {
        setMessage("Registration failed ❌");
      }

    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">

        <h2>Create Account</h2>
        <p className="subtitle">Register to get started</p>

        <input
          type="text"
          name="name"
          placeholder="Full Name"
          value={form.name}
          onChange={handleChange}
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
        />

        <button onClick={handleRegister} disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>

        {message && <p style={{ marginTop: "10px" }}>{message}</p>}

        <p style={{ marginTop: "10px", fontSize: "14px" }}>
          Already have an account?{" "}
          <span
            onClick={() => navigate("/login")}
            style={{ color: "#2563eb", cursor: "pointer", fontWeight: "500" }}
          >
            Login
          </span>
        </p>

      </div>
    </div>
  );
}

export default Register;