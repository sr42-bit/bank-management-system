import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

import Sidebar from "./components/Sidebar";
import Navbar from "./components/Navbar";
import UserSidebar from "./components/UserSidebar";
import Register from "./pages/admin/Register";
import Login from "./pages/admin/Login";

// ================= ADMIN =================
import Dashboard from "./pages/admin/Dashboard";
import Customers from "./pages/admin/Customers";
import CustomerDetails from "./pages/admin/CustomerDetails";
import Accounts from "./pages/admin/Accounts";
import Transactions from "./pages/admin/Transactions";

// ================= USER =================
import UserDashboard from "./pages/user/UserDashboard";
import UserAccounts from "./pages/user/UserAccounts";
import UserDeposit from "./pages/user/UserDeposit";
import UserWithdraw from "./pages/user/UserWithdraw";
import UserTransactions from "./pages/user/UserTransactions"; // ✅ FIX ADDED

// ================= PROTECTED =================
function ProtectedRoute({ children, allowedRoles = [] }) {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  if (!token) return <Navigate to="/login" />;

  if (allowedRoles.length > 0 && !allowedRoles.includes(role)) {
    return <Navigate to="/" />;
  }

  return children;
}

function App() {
  const token = localStorage.getItem("token");
  const role = localStorage.getItem("role");

  return (
    <BrowserRouter>

      {/* ================= SIDEBAR ================= */}
      {token && role === "ROLE_ADMIN" && <Sidebar />}
      {token && role === "ROLE_USER" && <UserSidebar />}

      <div
        style={{
          marginLeft:
            role === "ROLE_ADMIN" || role === "ROLE_USER" ? "240px" : "0px"
        }}
      >

        {/* ================= NAVBAR ================= */}
        {token && <Navbar />}

        <Routes>

          {/* ================= DEFAULT ================= */}
          <Route
            path="/"
            element={
              token
                ? role === "ROLE_ADMIN"
                  ? <Navigate to="/admin/dashboard" />
                  : <Navigate to="/user/dashboard" />
                : <Navigate to="/login" />
            }
          />

          {/* ================= AUTH ================= */}
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />

          {/* ================= ADMIN ================= */}
          <Route
            path="/admin/dashboard"
            element={
              <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                <Dashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/customers"
            element={
              <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                <Customers />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/customers/:id"
            element={
              <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                <CustomerDetails />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/accounts"
            element={
              <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                <Accounts />
              </ProtectedRoute>
            }
          />

          <Route
            path="/admin/transactions"
            element={
              <ProtectedRoute allowedRoles={["ROLE_ADMIN"]}>
                <Transactions />
              </ProtectedRoute>
            }
          />

          {/* ================= USER ================= */}
          <Route
            path="/user/dashboard"
            element={
              <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                <UserDashboard />
              </ProtectedRoute>
            }
          />

          <Route
            path="/user/accounts"
            element={
              <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                <UserAccounts />
              </ProtectedRoute>
            }
          />

          <Route
            path="/user/deposit"
            element={
              <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                <UserDeposit />
              </ProtectedRoute>
            }
          />

          <Route
            path="/user/withdraw"
            element={
              <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                <UserWithdraw />
              </ProtectedRoute>
            }
          />

          {/* ✅ FIX: ADD THIS ROUTE */}
          <Route
            path="/user/transactions"
            element={
              <ProtectedRoute allowedRoles={["ROLE_USER"]}>
                <UserTransactions />
              </ProtectedRoute>
            }
          />

        </Routes>
      </div>
    </BrowserRouter>
  );
}

export default App;