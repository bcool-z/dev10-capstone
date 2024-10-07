import { useState, useEffect } from "react";
import { searchUsers } from '../services/userService'; // Import your search function

function ManageInstructors() {
  const [instructors, setInstructors] = useState([]);
  const [searchQuery, setSearchQuery] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage] = useState(10);

  // Fetch instructors when the search button is clicked
  const fetchInstructors = async () => {
    try {
      const formattedQuery = searchQuery.replace(/\s+/g, "+"); // Replace spaces with plus signs
      const result = await searchUsers(formattedQuery);
      setInstructors(result);
    } catch (error) {
      console.error("Error fetching instructors:", error);
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

  // Pagination logic (unchanged)
  const indexOfLastInstructor = currentPage * itemsPerPage;
  const indexOfFirstInstructor = indexOfLastInstructor - itemsPerPage;
  const currentInstructors = instructors.slice(indexOfFirstInstructor, indexOfLastInstructor);
  const totalPages = Math.ceil(instructors.length / itemsPerPage);

  return (
    <div className="manage-instructors">
      <h1>Manage Instructors</h1>

      {/* Search Bar */}
      <div className="input-group mb-4">
        <input
          type="text"
          placeholder="Search instructors..."
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
            <th>Name</th>
            <th>Email</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {currentInstructors.map((instructor) => (
            <tr key={instructor.id}>
              <td>{instructor.name}</td>
              <td>{instructor.email}</td>
              <td>
                <button className="btn btn-primary">Edit</button>
                <button className="btn btn-danger">Delete</button>
              </td>
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

export default ManageInstructors;
