<app-message>

    <div class={'message': true, 'show': !!description}>
        <div class="description">{description}</div>
        <button class="button button-clear">Dispensar</button>
    </div>

    <style>

        :scope * {
            box-sizing: border-box;
        }

        :scope > .message {
            display: flex;
            align-items: center;
            position: fixed;
            bottom: 0;
            left: 0;
            right: 0;
            height: 0;
            background-color: black;
            color: #9A46C5;
            transition: height 0.5s;
        }

        :scope > .message.show {
            height: 5rem;
            padding: 0 3rem;
        }

        :scope > .message > button {
            margin-bottom: 0;
            padding: 0 1rem;
        }

        :scope > .message > .description {
            color: white;
            flex: 1;
        }

        @media (max-width: 40.0rem) {
            :scope > .message.show {
                height: 5rem;
                padding: 0 0.5rem;
            }
        }

    </style>

    <script>
        this.description = "Mensagem de teste";
    </script>
</app-message>