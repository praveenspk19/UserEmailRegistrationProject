import { useState } from "react";
import api from "../api/axios";

function SearchUsers() {
  const [query, setQuery] = useState("");
  const [users, setUsers] = useState([]);
  const [error, setError] = useState("");

  const handleSearch = async (e) => {
    e.preventDefault();
    setError("");
    try {
      const res = await api.get(`/api/search/users?username=${query}`);
      setUsers(res.data);
    } catch (err) {
      setUsers([]);
      setError(err.response?.data || "Search failed");
    }
  };

  return (
    <div className="container">
      <div className="card">
        <form onSubmit={handleSearch}>
          <h2>Search Users</h2>
          <input
            type="text"
            placeholder="Search by username or email"
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
          <button type="submit">Search</button>
        </form>

        {error && <p style={{ color: "red" }}>{error}</p>}

        <div style={{ marginTop: "20px" }}>
          {users.length > 0 ? (
            users.map((user) => (
              <div key={user.id} className="user-card">
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
              </div>
            ))
          ) : (
            query && <p>No users found</p>
          )}
        </div>
      </div>
    </div>
  );
}

export default SearchUsers;
