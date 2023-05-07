import {Table} from "react-bootstrap";
import React from "react";

export default function LogsViewer({logs: logs}) {
    return <Table striped bordered hover>
        <thead>
        <tr>
            <td>Date</td>
            <td>Host</td>
            <td>Process</td>
            <td>PID</td>
            <td>Message</td>
        </tr>
        </thead>
        <tbody>
        {logs.map((v, i) => <tr key={i}>
            <td>{v.date}</td>
            <td>{v.host}</td>
            <td>{v.process}</td>
            <td>{v.pid}</td>
            <td>{v.message}</td>
        </tr>)}
        </tbody>
    </Table>;
}