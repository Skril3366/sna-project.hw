import React from "react";
import {createRoot} from "react-dom/client";
import MainContainer from "./MainContainer";

const d = document.createElement("div");
document.body.append(d);
const root = createRoot(d);

root.render(<MainContainer/>);