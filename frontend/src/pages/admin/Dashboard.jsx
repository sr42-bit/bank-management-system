import { useEffect, useState } from "react";
import API from "../../services/api/api";
import { Bar, Line } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler  
} from "chart.js";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  PointElement,
  LineElement,
  Title,
  Tooltip,
  Legend,
  Filler  
);

function Dashboard() {
  const [stats, setStats] = useState({
    totalCustomers: 0,
    totalAccounts: 0,
    activeAccounts: 0,
    totalBalance: 0
  });

  const [accountGrowth, setAccountGrowth] = useState([]);
  const [customerGrowth, setCustomerGrowth] = useState([]);
  const [year, setYear] = useState(new Date().getFullYear());

  const months = [
    "Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
  ];

  useEffect(() => {
    API.get("/dashboard/stats")
      .then(res => setStats(res.data))
      .catch(err => console.log(err));

    API.get(`/dashboard/accounts-growth?year=${year}`)
      .then(res => setAccountGrowth(res.data))
      .catch(err => console.log(err));

    API.get(`/dashboard/customers-growth?year=${year}`)
      .then(res => setCustomerGrowth(res.data))
      .catch(err => console.log(err));
  }, [year]);

  const accountData = months.map(m => {
    const found = accountGrowth.find(a => a.month === m);
    return found ? found.count : 0;
  });

  const customerData = months.map(m => {
    const found = customerGrowth.find(c => c.month === m);
    return found ? found.count : 0;
  });

  const accountChart = {
    labels: months,
    datasets: [
      {
        label: "Accounts",
        data: accountData,
        backgroundColor: "rgba(102, 126, 234, 0.8)",
        borderColor: "#667eea",
        borderWidth: 1,
        borderRadius: 8,
        hoverBackgroundColor: "#5a67d8"
      }
    ]
  };

  const customerChart = {
    labels: months,
    datasets: [
      {
        label: "Customers",
        data: customerData,
        borderColor: "#48bb78",
        backgroundColor: "rgba(72, 187, 120, 0.1)",
        tension: 0.4,
        fill: true,
        pointBackgroundColor: "#48bb78",
        pointBorderColor: "#fff",
        pointHoverBackgroundColor: "#38a169",
        pointRadius: 6,
        pointHoverRadius: 8
      }
    ]
  };

  return (
    <div className="min-vh-100" style={{ background: "linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%)" }}>
      <div className="container-fluid px-4 py-5">
        {/* HEADER */}
        <div className="d-flex justify-content-between align-items-center mb-5">
          <h1 className="fw-bold text-dark mb-0" style={{ fontSize: "2.5rem", letterSpacing: "-0.02em" }}>Bank Dashboard</h1>
          <select
            className="form-select shadow-sm border-0 rounded-pill px-3 py-2"
            style={{ width: "140px", backgroundColor: "#fff", color: "#333", fontWeight: "500" }}
            value={year}
            onChange={(e) => setYear(e.target.value)}
          >
            <option value="2026">2026</option>
            <option value="2025">2025</option>
            <option value="2024">2024</option>
          </select>
        </div>

        {/* KPI CARDS */}
        <div className="row g-4 mb-5">
          <div className="col-lg-3 col-md-6">
            <div className="card border-0 shadow-lg p-4 h-100 rounded-4 position-relative overflow-hidden" style={{ background: "linear-gradient(135deg, #667eea 0%, #764ba2 100%)", color: "white", transition: "transform 0.3s ease" }} onMouseEnter={(e) => e.currentTarget.style.transform = "translateY(-5px)"} onMouseLeave={(e) => e.currentTarget.style.transform = "translateY(0)"}>
              <div className="position-absolute top-0 end-0 opacity-25" style={{ width: "100px", height: "100px", background: "radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%)", borderRadius: "50%", transform: "translate(30px, -30px)" }}></div>
              <h6 className="text-white-50 mb-2 fw-semibold">Total Customers</h6>
              <h2 className="fw-bold mb-0" style={{ fontSize: "2rem" }}>{stats.totalCustomers}</h2>
            </div>
          </div>
          <div className="col-lg-3 col-md-6">
            <div className="card border-0 shadow-lg p-4 h-100 rounded-4 position-relative overflow-hidden" style={{ background: "linear-gradient(135deg, #f093fb 0%, #f5576c 100%)", color: "white", transition: "transform 0.3s ease" }} onMouseEnter={(e) => e.currentTarget.style.transform = "translateY(-5px)"} onMouseLeave={(e) => e.currentTarget.style.transform = "translateY(0)"}>
              <div className="position-absolute top-0 end-0 opacity-25" style={{ width: "100px", height: "100px", background: "radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%)", borderRadius: "50%", transform: "translate(30px, -30px)" }}></div>
              <h6 className="text-white-50 mb-2 fw-semibold">Total Accounts</h6>
              <h2 className="fw-bold mb-0" style={{ fontSize: "2rem" }}>{stats.totalAccounts}</h2>
            </div>
          </div>
          <div className="col-lg-3 col-md-6">
            <div className="card border-0 shadow-lg p-4 h-100 rounded-4 position-relative overflow-hidden" style={{ background: "linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)", color: "white", transition: "transform 0.3s ease" }} onMouseEnter={(e) => e.currentTarget.style.transform = "translateY(-5px)"} onMouseLeave={(e) => e.currentTarget.style.transform = "translateY(0)"}>
              <div className="position-absolute top-0 end-0 opacity-25" style={{ width: "100px", height: "100px", background: "radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%)", borderRadius: "50%", transform: "translate(30px, -30px)" }}></div>
              <h6 className="text-white-50 mb-2 fw-semibold">Active Accounts</h6>
              <h2 className="fw-bold mb-0" style={{ fontSize: "2rem" }}>{stats.activeAccounts}</h2>
            </div>
          </div>
          <div className="col-lg-3 col-md-6">
            <div className="card border-0 shadow-lg p-4 h-100 rounded-4 position-relative overflow-hidden" style={{ background: "linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)", color: "white", transition: "transform 0.3s ease" }} onMouseEnter={(e) => e.currentTarget.style.transform = "translateY(-5px)"} onMouseLeave={(e) => e.currentTarget.style.transform = "translateY(0)"}>
              <div className="position-absolute top-0 end-0 opacity-25" style={{ width: "100px", height: "100px", background: "radial-gradient(circle, rgba(255,255,255,0.3) 0%, transparent 70%)", borderRadius: "50%", transform: "translate(30px, -30px)" }}></div>
              <h6 className="text-white-50 mb-2 fw-semibold">Total Balance</h6>
              <h2 className="fw-bold mb-0" style={{ fontSize: "2rem" }}>₹{Number(stats.totalBalance).toLocaleString()}</h2>
            </div>
          </div>
        </div>

        {/* CHARTS */}
        <div className="row g-4">
          <div className="col-lg-6">
            <div className="card border-0 shadow-lg p-4 rounded-4" style={{ background: "#fff" }}>
              <h5 className="fw-semibold mb-4 text-dark" style={{ fontSize: "1.25rem" }}>Account Growth</h5>
              <Bar
                data={accountChart}
                options={{
                  responsive: true,
                  plugins: { legend: { display: false } },
                  scales: { 
                    y: { 
                      beginAtZero: true, 
                      grid: { color: "rgba(0,0,0,0.05)" },
                      ticks: { color: "#666" }
                    }, 
                    x: { 
                      grid: { color: "rgba(0,0,0,0.05)" },
                      ticks: { color: "#666" }
                    } 
                  }
                }}
                height={250}
              />
            </div>
          </div>
          <div className="col-lg-6">
            <div className="card border-0 shadow-lg p-4 rounded-4" style={{ background: "#fff" }}>
              <h5 className="fw-semibold mb-4 text-dark" style={{ fontSize: "1.25rem" }}>Customer Growth</h5>
              <Line
                data={customerChart}
                options={{
                  responsive: true,
                  plugins: { legend: { display: false } },
                  scales: { 
                    y: { 
                      beginAtZero: true, 
                      grid: { color: "rgba(0,0,0,0.05)" },
                      ticks: { color: "#666" }
                    }, 
                    x: { 
                      grid: { color: "rgba(0,0,0,0.05)" },
                      ticks: { color: "#666" }
                    } 
                  }
                }}
                height={250}
              />
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Dashboard;
