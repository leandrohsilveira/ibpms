const path = require("path");
const webpack = require("webpack");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const sass = require("node-sass");

const extractCSS = new ExtractTextPlugin("[name].bundle.css");

module.exports = env => ({
  context: path.resolve(__dirname, "src"),
  entry: {
    app: "./index.js"
  },
  mode: env.STAGE,
  output: {
    path: path.resolve(__dirname, "public")
  },
  devtool: "source-map",
  optimization: {
    splitChunks: {
      chunks: "all"
    }
  },
  devServer: {
    contentBase: path.resolve(__dirname, "public"),
    port: 80,
    host: "0.0.0.0"
  },
  watchOptions: {
    aggregateTimeout: 300,
    poll: 1000
  },
  plugins: [
    new webpack.ProvidePlugin({ riot: "riot", riotRoute: "riot-route" }),
    new HtmlWebpackPlugin({
      template: "./index.html"
    }),
    extractCSS
  ],
  module: {
    rules: [
      {
        enforce: "pre",
        test: /\.tag$/,
        exclude: /node_modules/,
        loader: "customize-riotjs-loader",
        options: {
          type: "none",
          style: "scss",
          parsers: {
            css: {
              sass: (tagName, css, opts, url) => {
                const res = sass.renderSync({ data: css });
                // do work here
                return res.css.toString();
              }
            }
          },
          parserOptions: {
            style: (tagName, css, opts, url) => {
              var res = sass.renderSync({ data: css });
              // do work here
              return res.css.toString();
            }
          }
        }
      },
      {
        test: /\.js$|\.tag$|\.es6$/,
        exclude: /node_modules/,
        loader: "babel-loader"
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
      }
    ]
  }
});
