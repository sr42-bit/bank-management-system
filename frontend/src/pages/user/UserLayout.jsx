import Navbar from "../components/Navbar";
import UserSidebar from "../components/UserSidebar";

function UserLayout({ children }) {
  return (
    <div style={{ display: "flex" }}>

      {/* Sidebar */}
      <UserSidebar />

      {/* Right Side */}
      <div style={{ flex: 1 }}>

        {/* 🔥 Navbar (THIS WAS MISSING) */}
        <Navbar />

        {/* Page Content */}
        <div style={{ padding: "20px" }}>
          {children}
        </div>

      </div>
    </div>
  );
}

export default UserLayout;