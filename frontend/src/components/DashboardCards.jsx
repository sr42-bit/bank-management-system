import { useEffect, useState } from "react";
import api from "../api"; // your axios instance

function DashboardCards() {
  const [data, setData] = useState({
    totalCustomers: 0,
    totalAccounts: 0,
    activeAccounts: 0,
    closedAccounts: 0,
  });

  useEffect(() => {
    api.get("/dashboard") // adjust endpoint
      .then((res) => {
        setData(res.data);
      })
      .catch((err) => {
        console.error("Dashboard error:", err);
      });
  }, []);

  const cards = [
    { label: "Total Customers", value: data.totalCustomers, icon: "👥", color: "bg-blue-50" },
    { label: "Total Accounts", value: data.totalAccounts, icon: "💳", color: "bg-purple-50" },
    { label: "Active Accounts", value: data.activeAccounts, icon: "✓", color: "bg-green-50" },
    { label: "Closed Accounts", value: data.closedAccounts, icon: "✕", color: "bg-red-50" }
  ];

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
      {cards.map((card, index) => (
        <div
          key={index}
          className={`${card.color} rounded-lg p-6 border border-gray-200 backdrop-blur-sm hover:shadow-lg transition-all duration-300 hover:-translate-y-1`}
        >
          <div className="flex justify-between items-start">
            <div>
              <p className="text-gray-600 text-sm font-medium mb-2">{card.label}</p>
              <h3 className="text-3xl font-bold text-gray-900">{card.value}</h3>
            </div>
            <span className="text-2xl">{card.icon}</span>
          </div>
        </div>
      ))}
    </div>
  );
}

export default DashboardCards;