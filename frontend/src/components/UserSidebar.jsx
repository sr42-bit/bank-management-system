import { Link, useLocation } from "react-router-dom";

function UserSidebar() {
  const location = useLocation();

  const menu = [
    { name: "Dashboard", path: "/user/dashboard" },
    { name: "My Accounts", path: "/user/accounts" },
    { name: "Deposit", path: "/user/deposit" },
    { name: "Withdraw", path: "/user/withdraw" },
    { name: "Transactions", path: "/user/transactions" } // ✅ ADDED
  ];

  return (
    <div style={styles.sidebar}>
      <h2 style={styles.logo}>User Panel</h2>

      {menu.map((item) => (
        <Link
          key={item.path}
          to={item.path}
          style={{
            ...styles.link,
            background:
              location.pathname === item.path ? "#1f2937" : "transparent"
          }}
        >
          {item.name}
        </Link>
      ))}
    </div>
  );
}

const styles = {
  sidebar: {
    width: "220px",
    height: "100vh",
    background: "#111827",
    color: "white",
    padding: "20px",
    position: "fixed"
  },
  logo: {
    marginBottom: "30px"
  },
  link: {
    display: "block",
    padding: "10px",
    marginBottom: "10px",
    borderRadius: "6px",
    color: "white",
    textDecoration: "none"
  }
};

export default UserSidebar;