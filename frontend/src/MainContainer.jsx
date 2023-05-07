import React from "react";
import {Container, Navbar} from "react-bootstrap";
import LogSearch from "./LogSearch";

export default function MainContainer() {
    return <>
        <Navbar variant="dark" bg="dark">
            <Container>
                <Navbar.Brand>Logs collector search</Navbar.Brand>
            </Container>
        </Navbar>
        <Container>
            <LogSearch/>
        </Container>
    </>;
}