import {Button, Col, Form, Row} from "react-bootstrap";
import React, {useState} from "react";
import axios from "axios";
import LogsViewer from "./LogsViewer";

export default function LogSearch() {
    const [host, setHost] = useState("");
    const [date, setDate] = useState("");
    const [process, setProcess] = useState("");
    const [pid, setPid] = useState("");
    const [resp, setResp] = useState([{
        host: "test",
        date: "1234-56-78 90:10:11.1213",
        process: "/bin/test",
        pid: "1",
        message: "qwerty"
    },
        {
            host: "test",
            date: "1234-56-78 90:10:11.1213",
            process: "/bin/test",
            pid: "1",
            message: "wasd"
        }]);
    return <>
        <Form onSubmit={e => {
            e.preventDefault();
            e.stopPropagation();
            console.log({host: host, date: date, process: process, pid: pid});
            axios.get("/search", {params: {host: host, date: date, process: process, pid: pid}}).then(console.log);
        }
        } className="mb-3">
            <Form.Group as={Row} className="mb-3 mt-3" controlId="host">
                <Form.Label column sm={1}>
                    Host:
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="text" onChange={e => setHost(e.target.value)}/>
                </Col>
            </Form.Group>

            <Form.Group as={Row} className="mb-3" controlId="date">
                <Form.Label column sm={1}>
                    Date:
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="text" onChange={e => setDate(e.target.value)}/>
                </Col>
            </Form.Group>

            <Form.Group as={Row} className="mb-3" controlId="process">
                <Form.Label column sm={1}>
                    Process:
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="text" onChange={e => setProcess(e.target.value)}/>
                </Col>
            </Form.Group>

            <Form.Group as={Row} className="mb-3" controlId="pid">
                <Form.Label column sm={1}>
                    PID:
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="text" onChange={e => setPid(e.target.value)}/>
                </Col>
            </Form.Group>
            <Button type="submit">Search</Button>
        </Form>
        {resp !== undefined && <LogsViewer logs={resp}/>}
    </>;
}