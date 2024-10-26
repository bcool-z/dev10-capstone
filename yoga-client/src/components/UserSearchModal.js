import { useState, useEffect } from "react";
import { Modal, Button } from 'react-bootstrap';
import { searchUsers } from '../services/userService';

export default function UserSearchModal({ show, handleClose, onUserSelect }) {
  const [users, setUsers] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  const fetchUsers = async () => {
    try {
      const formattedQuery = searchQuery.replace(/\s+/g, "+");
      const result = await searchUsers(formattedQuery);
      setUsers(result);
    } catch (error) {
      console.error("Error fetching users:", error);
    }
  };

  const handleSearchClick = () => {
    setCurrentPage(1);
    fetchUsers();
  };

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const indexOfLastUser = currentPage * itemsPerPage;
  const indexOfFirstUser = indexOfLastUser - itemsPerPage;
  const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);
  const totalPages = Math.ceil(users.length / itemsPerPage);

  return (
    <Modal show={show} onHide={handleClose}>
      <Modal.Header closeButton>
        <Modal.Title>Search Users</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <div className="input-group mb-4">
          <input
            type="text"
            placeholder="Search users..."
            value={searchQuery}
            onChange={(e) => setSearchQuery(e.target.value)}
            className="form-control"
          />
          <Button onClick={handleSearchClick}>Search</Button>
        </div>

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
              <tr key={user.id} onDoubleClick={() => onUserSelect(user)}>
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

        <div className="pagination">
          {Array.from({ length: totalPages }, (_, i) => (
            <Button
              key={i + 1}
              onClick={() => handlePageChange(i + 1)}
              className={`btn ${currentPage === i + 1 ? "btn-primary" : "btn-secondary"}`}
            >
              {i + 1}
            </Button>
          ))}
        </div>
      </Modal.Body>
    </Modal>
  );
}