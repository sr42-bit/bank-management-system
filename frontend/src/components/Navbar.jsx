import { useNavigate } from "react-router-dom";

function Navbar() {
  const navigate = useNavigate();

  const role = localStorage.getItem("role");

  // ✅ safe fallback
  const displayRole =
    role === "ROLE_ADMIN"
      ? "Admin"
      : role === "ROLE_USER"
      ? "User"
      : "Guest";

  const initial =
    role === "ROLE_ADMIN"
      ? "A"
      : role === "ROLE_USER"
      ? "U"
      : "G";

  const handleLogout = () => {
    // 🔥 clear EVERYTHING related to auth
    localStorage.removeItem("token");
    localStorage.removeItem("role");
    localStorage.removeItem("customerId");

    // ✅ clean redirect (NO reload needed)
    navigate("/login");
  };

  // ❌ If not logged in → don't show navbar
  const token = localStorage.getItem("token");
  if (!token) return null;

  return (
    <div className="d-flex justify-content-between align-items-center px-4 py-3 bg-white shadow-sm">

      {/* LEFT */}
      <div className="fs-5 fw-bold">
        {displayRole} Dashboard
      </div>

      {/* RIGHT */}
      <div className="d-flex align-items-center gap-3">

        <div className="d-flex align-items-center gap-2">
          <span className="text-muted fw-500">{displayRole}</span>
        </div>

        {/* Avatar (Logout) */}
        <div
          className="rounded-circle text-white d-flex align-items-center justify-content-center fw-bold"
          style={{
            width: "40px",
            height: "40px",
            background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)",
            fontSize: "16px",
            cursor: "pointer"
          }}
          title="Logout"
          onClick={handleLogout}
        >
          {initial}
        </div>

      </div>
    </div>
  );
}

export default Navbar;