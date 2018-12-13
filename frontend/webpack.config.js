const path = require('path')
const webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require("extract-text-webpack-plugin");

const extractCSS = new ExtractTextPlugin('[name].bundle.css');

module.exports = {
    context: path.resolve(__dirname, 'src'),
    entry: {
        app: './index.js',
    },
    mode: 'development',
    output: {
        path: path.resolve(__dirname, 'public'),
    },
    devtool: 'source-map',
    optimization: {
        splitChunks: {
            chunks: 'all'
        }
    },
    devServer: {
        contentBase: path.resolve(__dirname, 'public'),
        port: 80,
        host: '0.0.0.0'
    },
    watchOptions: {
        aggregateTimeout: 300,
        poll: 1000
    },
    plugins: [
        new webpack.ProvidePlugin({riot: 'riot', riotRoute: 'riot-route'}),
        new HtmlWebpackPlugin({
            template: './index.html'
        }),
        extractCSS,
    ],
    module: {
        rules: [
            {
                enforce: 'pre',
                test: /\.tag$/,
                exclude: /node_modules/,
                loader: 'customize-riotjs-loader',
                query: { type: 'none' }
            },
            {
                test: /\.js$|\.tag$|\.es6$/,
                exclude: /node_modules/,
                loader: 'babel-loader',
            },
            {
                test: /\.(css)$/,
                use: extractCSS.extract({
                    fallback: "style-loader",
                    use: "css-loader"
                })
            },
            {
                test: /\.(jpe?g|png|gif|svg|eot|woff|ttf|woff2)$/,
                loader: "file-loader"
            },
        ]
    }
}