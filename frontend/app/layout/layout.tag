<app-layout>
    <header class="header">
        <div class="container">
            <img src="assets/images/logo.jpg" alt="Logo">
            <h1>IBPMS</h1>
        </div>
    </header>

    <main class="content">
        <div class="container">
            <yield>
        </div>
    </main>

    <style>

        .header * {
            box-sizing: border-box;
        }

        .header {
            width: 100%;
            padding: 10px 0;
            -webkit-box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
            -moz-box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
            box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
        }

        .header > .container {
            display: flex;
            align-items: center;
        }

        .header > .container > h1 {
            margin: 0 10px;
            padding: 0;
        }

        .content {
            margin-top: 10px;
        }

    </style>

</app-layout>