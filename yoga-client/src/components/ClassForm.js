import React, { useState, useEffect } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import { fetchLocations } from '../services/locationService'; // Assumes a function to fetch locations
// import InstructorSearchModal from './InstructorSearchModal'; // Reusable search modal


export default function ClassForm({ initialSession, onSave }) {
    const [session, setSession] = useState(initialSession || { id: 0, start: '', end: '', capacity: '', instructor: null, location: null });
    const [locations, setLocations] = useState([]);
    const [showInstructorModal, setShowInstructorModal] = useState(false);

    useEffect(() => {
        // Fetch available locations on load
        fetchLocations().then(setLocations);
    }, []);

    const handleSave = () => onSave(session);

    const handleInstructorSelect = (instructor) => {
        setSession({ ...session, instructor });
        setShowInstructorModal(false);
    };

    return (
        <div>
            <h2>{session.id === 0 ? 'New Class' : 'Edit Class'}</h2>
            <Form>
                <Form.Group>
                    <Form.Label>Start Date & Time</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={session.start}
                        onChange={(e) => setSession({ ...session, start: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>End Date & Time</Form.Label>
                    <Form.Control
                        type="datetime-local"
                        value={session.end}
                        onChange={(e) => setSession({ ...session, end: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Capacity</Form.Label>
                    <Form.Control
                        type="number"
                        value={session.capacity}
                        onChange={(e) => setSession({ ...session, capacity: e.target.value })}
                    />
                </Form.Group>

                <Form.Group>
                    <Form.Label>Location</Form.Label>
                    <Form.Control
                        as="select"
                        value={session.location?.id || ''}
                        onChange={(e) => setSession({ ...session, location: locations.find(loc => loc.id === parseInt(e.target.value)) })}
                    >
                        <option value="">Select a location</option>
                        {locations.map(loc => (
                            <option key={loc.id} value={loc.id}>{loc.name}</option>
                        ))}
                    </Form.Control>
                </Form.Group>

                <Form.Group>
                    <Form.Label>Instructor</Form.Label>
                    <Button variant="secondary" onClick={() => setShowInstructorModal(true)}>
                        {session.instructor ? `${session.instructor.firstName} ${session.instructor.lastName}` : 'Select Instructor'}
                    </Button>
                </Form.Group>

                <Button variant="primary" onClick={handleSave}>Save</Button>
            </Form>

            {/* Instructor Selection Modal */}
            <InstructorSearchModal
                show={showInstructorModal}
                onHide={() => setShowInstructorModal(false)}
                onSelect={handleInstructorSelect}
            />
        </div>
    );
}