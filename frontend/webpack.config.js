const path = require("path");
const distPath = path.resolve(__dirname, "./dist");
module.exports = {
    mode: "development",
    target: "web",
    devtool: "source-map",
    module: {
        rules: [
            {
                test: /\.js(x?)$/,
                exclude: /(node_modules)/,
                use: [{
                    loader: 'babel-loader',
                    options: {
                        presets: [
                            '@babel/preset-react'
                        ]
                    }
                }]
            }
        ]
    },
    resolve: {
        extensions: [".jsx", ".js"]
    },

    entry: "./src/index.jsx",
    output: {
        path: distPath,
        filename: "index.js"
    }
};