import { useEffect, useState } from "react";

const apiBase = "https://vinayhalwan-cogpractice.onrender.com/api/customers";

const emptyCustomer = {
  id: "",
  name: "",
  email: "",
};

const pageStyles = {
  wrapper: {
    maxWidth: 820,
    margin: "40px auto",
    padding: "0 20px",
    fontFamily: "Arial, sans-serif",
  },
  panel: {
    background: "#f4f4f4",
    padding: 20,
    borderRadius: 10,
    marginBottom: 28,
  },
  input: {
    marginRight: 10,
    padding: 8,
  },
  primaryButton: {
    padding: "8px 16px",
    background: "#1976d2",
    color: "#fff",
    border: "none",
    borderRadius: 4,
    cursor: "pointer",
  },
  secondaryButton: {
    marginLeft: 10,
    padding: "8px 16px",
    border: "1px solid #ccc",
    background: "#fff",
    borderRadius: 4,
    cursor: "pointer",
  },
  table: {
    width: "100%",
    borderCollapse: "collapse",
  },
  headerRow: {
    background: "#1976d2",
    color: "#fff",
  },
  cell: {
    padding: 10,
    textAlign: "left",
  },
};

export default function CustomerDashboard() {
  const [customerList, setCustomerList] = useState([]);
  const [customerForm, setCustomerForm] = useState(emptyCustomer);
  const [isEditing, setIsEditing] = useState(false);
  const [lookupId, setLookupId] = useState("");
  const [matchedCustomer, setMatchedCustomer] = useState(null);

  useEffect(() => {
    loadCustomers();
  }, []);

  const resetForm = () => {
    setCustomerForm(emptyCustomer);
  };

  const loadCustomers = () => {
    fetch(apiBase)
      .then((res) => res.json())
      .then((data) => setCustomerList(data));
  };

  const handleSave = () => {
    if (isEditing) {
      fetch(`${apiBase}/${customerForm.id}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(customerForm),
      }).then(() => {
        loadCustomers();
        resetForm();
        setIsEditing(false);
      });
      return;
    }

    fetch(apiBase, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ ...customerForm, accounts: [] }),
    }).then(() => {
      loadCustomers();
      resetForm();
    });
  };

  const handleRemove = (id) => {
    fetch(`${apiBase}/${id}`, {
      method: "DELETE",
    }).then(() => loadCustomers());
  };

  const handleStartEdit = (customer) => {
    setCustomerForm({
      id: customer.id,
      name: customer.name,
      email: customer.email,
    });
    setIsEditing(true);
  };

  const handleSearch = () => {
    fetch(`${apiBase}/${lookupId}`)
      .then((res) => res.json())
      .then((data) => setMatchedCustomer(data));
  };

  const handleCancelEdit = () => {
    setIsEditing(false);
    resetForm();
  };

  return (
    <div style={pageStyles.wrapper}>
      <h1>Banking App</h1>

      <div style={pageStyles.panel}>
        <h2>{isEditing ? "Edit Customer" : "Add Customer"}</h2>

        <input
          placeholder="ID"
          value={customerForm.id}
          onChange={(e) =>
            setCustomerForm({ ...customerForm, id: e.target.value })
          }
          style={pageStyles.input}
          disabled={isEditing}
        />
        <input
          placeholder="Name"
          value={customerForm.name}
          onChange={(e) =>
            setCustomerForm({ ...customerForm, name: e.target.value })
          }
          style={pageStyles.input}
        />
        <input
          placeholder="Email"
          value={customerForm.email}
          onChange={(e) =>
            setCustomerForm({ ...customerForm, email: e.target.value })
          }
          style={pageStyles.input}
        />

        <button onClick={handleSave} style={pageStyles.primaryButton}>
          {isEditing ? "Update" : "Add"}
        </button>

        {isEditing && (
          <button onClick={handleCancelEdit} style={pageStyles.secondaryButton}>
            Cancel
          </button>
        )}
      </div>

      <div style={pageStyles.panel}>
        <h2>Find Customer by ID</h2>

        <input
          placeholder="Enter ID"
          value={lookupId}
          onChange={(e) => setLookupId(e.target.value)}
          style={pageStyles.input}
        />
        <button onClick={handleSearch} style={pageStyles.primaryButton}>
          Search
        </button>

        {matchedCustomer && (
          <p style={{ marginTop: 10 }}>
            Found: <strong>{matchedCustomer.name}</strong> —{" "}
            {matchedCustomer.email}
          </p>
        )}
      </div>

      <h2>All Customers</h2>

      {customerList.length === 0 ? (
        <p>No customers yet.</p>
      ) : (
        <table style={pageStyles.table}>
          <thead>
            <tr style={pageStyles.headerRow}>
              <th style={pageStyles.cell}>ID</th>
              <th style={pageStyles.cell}>Name</th>
              <th style={pageStyles.cell}>Email</th>
              <th style={pageStyles.cell}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {customerList.map((customer) => (
              <tr key={customer.id} style={{ borderBottom: "1px solid #ddd" }}>
                <td style={pageStyles.cell}>{customer.id}</td>
                <td style={pageStyles.cell}>{customer.name}</td>
                <td style={pageStyles.cell}>{customer.email}</td>
                <td style={pageStyles.cell}>
                  <button
                    onClick={() => handleStartEdit(customer)}
                    style={{ marginRight: 8, padding: "4px 12px" }}
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleRemove(customer.id)}
                    style={{
                      padding: "4px 12px",
                      background: "red",
                      color: "white",
                      border: "none",
                      borderRadius: 4,
                    }}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}