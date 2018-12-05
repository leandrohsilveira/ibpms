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

        :scope .header * {
            box-sizing: border-box;
        }

        :scope .header {
            width: 100%;
            padding: 20px 0;
            -webkit-box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
            -moz-box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
            box-shadow: 0px 1px 5px 0px rgba(0,0,0,0.75);
        }

        :scope .header > .container {
            display: flex;
            align-items: center;
        }

        :scope .header > .container > h1 {
            margin: 0 10px;
            padding: 0;
        }

        :scope .content {
            margin-top: 20px;
        }

    </style>

</app-layout>