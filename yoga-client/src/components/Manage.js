import { useState, useEffect } from "react";
import { searchUsers,saveUser } from '../services/userService'; // Import your search function
import { Modal, Button } from 'react-bootstrap'; 
import { useNavigate } from "react-router-dom";



function ManageUsers() {
  const [users, setUsers] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  const navigate = useNavigate();
  // Fetch instructors when the search button is clicked
  const fetchInstructors = async () => {
    try {
      const formattedQuery = searchQuery.replace(/\s+/g, "+"); // Replace spaces with plus signs
      const result = await searchUsers(formattedQuery);
      setUsers(result);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const handleSearchChange = (e) => {
    setSearchQuery(e.target.value);
  };

  const handleSearchClick = () => {
    setCurrentPage(1); // Reset to the first page on a new search
    fetchInstructors();
  };

  // Handle page change
  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleRowDoubleClick = (user) => {
    console.log(user.id)
    navigate(`/profile/${user.appUserId}`, { state: { user } });
  };


  // Pagination logic (unchanged)
  const indexOfLastUser = currentPage * itemsPerPage;
  const indexOfFirstUser = indexOfLastUser - itemsPerPage;
  const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);
  const totalPages = Math.ceil(users.length / itemsPerPage);

  return (
    <div className="manage-userss">
      <h1>Manage Users</h1>

      {/* Search Bar */}
      <div className="input-group mb-4">
        <input
          type="text"
          placeholder="Search users..."
          value={searchQuery}
          onChange={handleSearchChange}
          className="form-control"
        />
        <button
          onClick={handleSearchClick}
          className="btn btn-primary"
        >
          Search
        </button>
      </div>

      {/* Instructor List */}
      <table className="table table-bordered">
        <thead>
          <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Birthday</th>
            <th>Phone</th>
            <th>Email</th>
            <th>Instructor</th>
          </tr>
        </thead>
        <tbody>
          {currentUsers.map((user) => (
            <tr key={user.id} onDoubleClick={()=>handleRowDoubleClick(user)}>
              <td>{user.firstName}</td>
              <td>{user.lastName}</td>
              <td>{user.dob}</td>
              <td>{user.phoneNumber}</td>
              <td>{user.emailAddress}</td>
              <td>{user.userType === "INSTRUCTOR" ? "✔️" : ""}</td>
              
            </tr>
          ))}
        </tbody>
      </table>

      {/* Pagination */}
      <div className="pagination">
        {Array.from({ length: totalPages }, (_, i) => (
          <button
            key={i + 1}
            onClick={() => handlePageChange(i + 1)}
            className={`btn ${currentPage === i + 1 ? "btn-primary" : "btn-secondary"}`}
          >
            {i + 1}
          </button>
        ))}
      </div>
    </div>
  );
}

export default ManageUsers;
