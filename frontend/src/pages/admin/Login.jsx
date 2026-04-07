import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../services/api/api";
import "./Login.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleLogin = async () => {
    if (!email || !password) {
      alert("Enter email and password");
      return;
    }

    try {
      setLoading(true);

      const res = await api.post("/auth/login", {
        email,
        password
      });

      const { token, role, customerId } = res.data;

      // ✅ store token
      localStorage.setItem("token", token);

      // ✅ normalize role
      const finalRole = role?.includes("ADMIN")
        ? "ROLE_ADMIN"
        : "ROLE_USER";

      localStorage.setItem("role", finalRole);

      // ✅ store customerId
      if (customerId) {
        localStorage.setItem("customerId", customerId);
      }

      // ✅ redirect
      navigate(
        finalRole === "ROLE_ADMIN"
          ? "/admin/dashboard"
          : "/user/dashboard"
      );

    } catch (err) {
      console.error(err);
      alert("Invalid credentials");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <div className="login-card">

        <h2>Welcome Back 👋</h2>
        <p className="subtitle">Login to your account</p>

        <input
          type="email"
          placeholder="Email address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={handleLogin} disabled={loading}>
          {loading ? "Logging in..." : "Login"}
        </button>

        {/* ✅ REGISTER LINK */}
        <p style={{ marginTop: "10px", fontSize: "14px" }}>
          Don't have an account?{" "}
          <span
            onClick={() => navigate("/register")}
            style={{ color: "#2563eb", cursor: "pointer", fontWeight: "500" }}
          >
            Register
          </span>
        </p>

      </div>
    </div>
  );
}

export default Login;