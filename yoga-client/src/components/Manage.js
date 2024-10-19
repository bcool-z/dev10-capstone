import { useState, useEffect } from "react";
import { searchUsers,saveUser } from '../services/userService'; // Import your search function
import { Modal, Button } from 'react-bootstrap'; 

function ManageUsers() {
  const [users, setUsers] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);
  const [showEditModal, setShowEditModal] = useState(false);
  const [selectedUser, setSelectedUser] = useState(null);

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
    setSelectedUser(user);
    setShowEditModal(true);
  };

  const [isEditingFirstName, setIsEditingFirstName] = useState(false);
  // const handleEdit = (user) => {
  //   setSelectedUser(user);
  //   setShowEditModal(true);
  // };

  // Close the modal
  const handleCloseModal = () => {
    setShowEditModal(false);
    setSelectedUser(null); // Clear the selected user
  };

  // Handle saving the edited user details
  const handleSave = (selectedUser) => {
    // Implement save logic (e.g., update the user on the server)
    saveUser(selectedUser);

    // After saving, close the modal
    setShowEditModal(false);
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

    {/* Edit User Modal */}
    {selectedUser && (
        <Modal show={showEditModal} onHide={handleCloseModal}>
          <Modal.Header closeButton>
            <Modal.Title>Edit User</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <form>
            <div className="mb-3">
  <label>First Name</label>
  {!isEditingFirstName ? (
    <>
      {/* Non-editable box styled like an input */}
      <input
        type="text"
        className="form-control"
        value={selectedUser.firstName}
        disabled
      />
      <button
        type="button"
        className="btn btn-link"
        onClick={() => setIsEditingFirstName(true)}
      >
        Edit
      </button>
    </>
  ) : (
    <>
      {/* Editable input field */}
      <input
        type="text"
        className="form-control"
        value={selectedUser.firstName}
        onChange={(e) =>
          setSelectedUser({
            ...selectedUser,
            firstName: e.target.value,
          })
        }
      />
      <button
        type="button"
        className="btn btn-link"
        onClick={() => setIsEditingFirstName(false)}
      >
        OK
      </button>
    </>
  )}
</div>
              <div className="mb-3">
                <label>Last Name</label>
                <input
                  type="text"
                  className="form-control"
                  value={selectedUser.lastName}
                  onChange={(e) =>
                    setSelectedUser({
                      ...selectedUser,
                      lastName: e.target.value,
                    })
                  }
                />
              </div>
              <div className="mb-3">
                <label>Birthday</label>
                <input
                  type="date"
                  className="form-control"
                  value={selectedUser.dob}
                  onChange={(e) =>
                    setSelectedUser({
                      ...selectedUser,
                      dob: e.target.value,
                    })
                  }
                />
              </div>
              <div className="mb-3">
                <label>Phone Number</label>
                <input
                  type="text"
                  className="form-control"
                  value={selectedUser.phoneNumber}
                  onChange={(e) =>
                    setSelectedUser({
                      ...selectedUser,
                      phoneNumber: e.target.value,
                    })
                  }
                />
              </div>
              <div className="mb-3">
                <label>Email</label>
                <input
                  type="email"
                  className="form-control"
                  value={selectedUser.emailAddress}
                  onChange={(e) =>
                    setSelectedUser({
                      ...selectedUser,
                      emailAddress: e.target.value,
                    })
                  }
                />
              </div>
            </form>
          </Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleCloseModal}>
              Close
            </Button>
            <Button variant="primary" onClick={handleSave}>
              Save Changes
            </Button>
          </Modal.Footer>
        </Modal>
      )}

    </div>
  );
}

export default ManageUsers;
