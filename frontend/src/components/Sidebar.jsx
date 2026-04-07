import { NavLink } from "react-router-dom";
import {
  FaChartBar,
  FaUsers,
  FaCreditCard,
  FaExchangeAlt
} from "react-icons/fa";

function Sidebar() {

  const linkStyle = ({ isActive }) =>
    `nav-link d-flex align-items-center text-white px-3 py-2 rounded ${
      isActive ? "bg-primary" : ""
    }`;

  return (

    <div
      style={{
        width: "240px",
        height: "100vh",
        position: "fixed",
        left: 0,
        top: 0,
        background: "#111827",
        color: "white",
        padding: "20px"
      }}
    >

      <h4 className="mb-5 fw-bold">🏦 BMS</h4>

      <ul className="nav flex-column gap-2">

        {/* ✅ FIXED */}
        <li>
          <NavLink to="/admin/dashboard" className={linkStyle}>
            <FaChartBar className="me-2"/>
            Dashboard
          </NavLink>
        </li>

        <li>
          <NavLink to="/admin/customers" className={linkStyle}>
            <FaUsers className="me-2"/>
            Customers
          </NavLink>
        </li>

        <li>
          <NavLink to="/admin/accounts" className={linkStyle}>
            <FaCreditCard className="me-2"/>
            Accounts
          </NavLink>
        </li>

        <li>
          <NavLink to="/admin/transactions" className={linkStyle}>
            <FaExchangeAlt className="me-2"/>
            Transactions
          </NavLink>
        </li>

      </ul>

    </div>

  );
}

export default Sidebar;