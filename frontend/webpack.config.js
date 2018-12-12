const path = require('path')
const webpack = require('webpack');
var HtmlWebpackPlugin = require('html-webpack-plugin');

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
        port: 80
    },
    plugins: [
        new webpack.ProvidePlugin({riot: 'riot', route: 'riot-route'}),
        new HtmlWebpackPlugin({
            template: './index.html'
        })
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
            }
        ]
    }
}