# YBrowser

一个轻量化的浏览器，提供书签、历史记录、云同步等功能。

![avatar][logo]









[logo]:data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAADICAIAAAAiOjnJAAAWsElEQVR4Ae2dPewdxRHAwWA7wR/I5sMoGBQlTUyBIoGU4AKngSYUREQp6KhwlVRQQQUVVEkFFV2qIFEkKXCDKUwigRRR4DShAIMwn8IfJNiA83veaLW5253bu9vZ2723lvXXvr293dmZ387M7d27d+2VK1euaf+aBlJrYEfqDlt/TQMbDTSwGgcqGmhgqai1ddrAagyoaKCBpaLW1mkDqzGgooEGlopaW6cNrMaAigYaWCpqbZ02sBoDKhpoYKmotXXawGoMqGiggaWi1tZpA6sxoKKBBpaKWlunDazGgIoGGlgqam2dNrAaAyoaaGCpqLV12sBqDKhooIGlotbWaQOrMaCigQaWilpbpw2sxoCKBhpYKmptnTawGgMqGrhepddqOz136bszF7/54MJlCucuf8c8zlz4hr/24/6dm6V4eO9Gb/t37eDj7Xt3Ht5zvamhsv0zGthqsMDlnS8ugREw/f3svw1V/J0GB3gdObjbQHbkwO6fH/retH7Wcda12/ZSEAPT6S++PvH+RaiajFGM+R+4Y89dB3b97ND3txCybQELgF5+93wGmELAARl4PfKjfQTQUJs11a8cLOOfXvrnlybSlWA5CHvw8A2P/HhfCcLoybBasP529j/4J7yUarCbY5jHfnKjcWNzOin23LWBZVzUs29+Sv5UrNJdwUjCIGx9Dmw9YIEU8e73b39RC1LrxmsNYIEUIe+l01+ya+Baq7oyef3T99y0Du9VPVjkUk+e+jgtUmYvat+u69j/NBdx/9sR3bnDDATK/AfcDy5eZgc17ejrwKtisAh5BD4y9JmeCUOy1USuw3+zjW5gGtUteLE3hkgUTpy5aLAb1UOnMcK8cOy2ejf0qwQLs4HUnCs+AxMbSwapjlHnf0RCiN9cmc6DjLz+6Xtvmi9P/h7qA2tO7CPGkcGYDfFsusaNcVUxOQWsNDLWBJZxVOx2jmUC2zxweM9jR24kvow9N2F7s7U2QX5kqM51VQPWtIyKYId/Ku1GClHymTc/G5vy15V11QEWa/34yY9GZcQg9du7D/I3octJ2xWTwnuNvfh4/r5bqtiPKB2sCeEPmJ669+Zlo148gnhi0i8uROJPqSIsFg0WVD3xxifxaxqYfnf3AWJfvJEKaQlex1/7KD44MtM///JwIcJ7xSgXrFFUkZ6DVGm5lFfjQiWRkdxLaOAegq0/PvADJu5WllMuFKxRK5jY99x9t9a7l+jSwHKCrcjIyO7JC7+4rcygXyJY8VSxXklma4x9Lkz9Mhp49MSHQNY/1KlBA/itAtkqDqx4na7JUXVw4SNUcSHMlWP/UL8Gtkq7/i0LrHiquDIiqSo2w+jbfloNd674H3MuuXxRfqsgsLh9i/8fvDJaa/gL0RO5h1daTCwFLDw/VOGxQvo19aSruP115OnyTN2jLDnC4qByYOv1h+8sxIsXcbEKVexXDSrOXGBvG1UQxpRjMnSzOF0iFywv77EMVYO7oCSnPJ9UyHJcymA49cF0HkVB4VIS2nGX91gxD+uxodCowmYQM7i3AnmPnzxrDbxUYWGwXv7X+cHHSNhPZ7Nqy32V5ePFY4cG2cL9D0YA26FSYUmwyEmfeWvgDgZ5FY9QNqpc88PW4K5VTM7q9pm8vBhYpFZPvvExf4UpmWy9UdVXEYmBvGuFYrmr3T8xW81iYJFayXno5kbY1mfrIQ5YbIPXiewILphsLQMWGYCcWqE4bq9u4c5CiKR+vWFLducLJlsLgIWXHrxNQbYuu/q+orewZrP8jt0mT5xkS26gdHQBsPBV8l4o9wEHL3yU1FFdt2TxqEsQm2X8xKkF2MoNFleCchBEU9xdFjTVDnU0wFWzfJHI013ySu50mORjbrDkK0F8O4/syXlDkmmX1gl+BdtzNcPCmyAbAZFrHeFE3kIgHNU4JEmTfDxySflKkNRqqxJ2YOK7rO98fsn9wvRmQ/joLaOUv1mQR2/lhk/oLAZiLzrn13vy3StkUT70lzPCUzHkVWz9hVSzmnr0AEyvvv+VedeDd14kAxPyATYXhA134PvHb37oHU6jMp/HItILVDHtCarU0IhGnybSwROGRwl8lEchDZ2gDfz9T8OvSGFQupUzfVmqUUczgbWZ1Wnpq/FMeGX7C2NhGmU2b2MWJ2wJ+wvs8qwNLLNSveqgksQz24RDMqSq96ZNYzuXM3GhN7IoYTcH1rNlWpk8lrzFQOLJahP0VfghDDaYNo2aAi9XGtXebcwV4v2vvOfWuGXu+udJ4XOAhbtiHbvTc8vk7PI2jNu4nLJqpJujEC6rua4MfTMxm9PKAZbsrioKgqowuWtmjseiHxL/EFgczeO01MHCVwl7VyzNOavTNYZe2aRNzIJ4B1vzByLumzcJ0nN/gwCFzEwMcFrEgX7PRnKmwCHtm2bqYIWmZybJm4bm20mjB7SfNm2CFS574QmLkpsbdLxbmjPdldEGTkvQPLtodYOFebgMCRm+NHeFtMY5YZKY3abQvNx6CxOFjh9iOK8vT7LtQif8ZzquMLbMRv8114zb3LfnRhZ0PRaLXtgULSG70oAJh4TXYdngFTowuVZBOe5HU6Z9Eo9Fbzit0IN+ZsGrXh7qgvWndy/0dZdcg6EhhHrrmSigZaFl5CEDBE4CmCJdjtddQZXAYqQwppmJuaGFTXZfK1gYzLsozbQXeZcVtiQ0CzfpRlkOAmAIFIwJxwLhzYEioYyUE8FCl+Sogicp9G75K3os1oTgDJhzpHZSNZPv0caPYmGiMBYmOwqe0utLWG+2zfwCyUYILDon09LLRhTB8rp6oyxMknmXAf16PUSk8SLTpsjeaHb686/7jRklrQuhN/r0EszoGKhKsLy6M9rM767kW+B9G1ODNyLMsQaQlr/eNpMrXz3zVf/cVGm72zNvtw+9flJIVNweppW1PFbI1Rsp0zr8wZnLwrinz0yb3K7ksnfVPXjHDfJZE46i6hBYJCo4LaXQoQWWsBqSO/xBdQvCmHNxSCYHpzA5bRoUwzYIgX7kwG7bJlWB6QjREM1UBpaQYB05mF59shm8wqBuYBrcbZJ7nnbUCzpMp02wrGzCtSGI22ZpCwt4LH5qO+0cBnvzxh2+3JI/1TOiekHXSLDMcCye0LUhFzQERA0nrfIUFOtA2GjQ06CXsJxxxytApxLNeD2WUkhidFnhSk5LBSyvhzD61XP4HfvZj14r5hfDyhNadRoJlhnUpFlWgE6B7eJOTZKPKmAJi0BPfSF1ZI47ITFs/SKgC0GfL59Z2RIWVMAK7cght0Y4F9SRP+4IwphDXrAEww92GNNAUPu5ywlulfZlUAFLCIV6mUR/btRgxX62h5bltMPbVZJKhPF6UEJzkv5DnQj9e0EP9RNfrwKW4LEyh0JvUEbLwgqO192Ell4rZgBdWEiw3l97E6bWOSU9WF5b2lEzW9RrSEHLVk6lgtddIY+2WuhfGEJwBJP1kH4f64MLl0PSsCcpTC901uT6UNyJBIsVYrZ58HCpHl3y3ggX4tTkufdPRPnvXPLn6Sy/5DKkB0vwq0o7y30lmhqvu4LsmDwPAvhKsZ0LG4zzf3gSUr2+Ic+d09v37pSDSUiN0+rTh0LhKuP2PTunSTntLL4y0D8xxl3BEzduLVV0gkl4o0m/t1E13msaHEme9cZAIWndmYbajK1PD9ZYCfTaez1WjM/Hr/Rdy3y2sj0qo6fS+J7TgyXgnzPBmhN39u/0q2UmW16PpfGojNf8gvKnve3NO4qt9GvQHp5Q0JByghghdxUTd2gTcmyT2QqBnnn/xatJIXvxto+pTA+WMKqwaISzph3yXtjHW1F4xfw0tuaAPk0DnbOEHKvTMsnH9GAJ+Oec28y4g9PiDf2hlTCBLS/oMVcSScxMJ6G5cOj8pW9TjWL7SQ+W7bpfEJjrN55TkyTuEA1TsUXe6fVYMRsfc/Tgnivkvm6zVOX0YIUy31QSx/TjtSKgxCRYbv+p2AJ0r13jQ7MrVfLyvl3XJe9TAazwK9S8yk0+JTpMGHeSsJUK9Dm6EsKFhi9ID5Yw+TxgMYrXkJPjziBbwotPjDa88mg/KtMxhKB8If3qdBL/MT1YgpTC3OIlHmyJFfsDIdWcTFlmK/REuREVYbwelD4H55KwQV8nCTvvd5UXLJ1nyjqzIqHp1PARKwrE99v3awS25J697mom6H3xBmsEsGT5B3v2NlAAK7Bt7R1eo9JryDnuygoZYksOal53hTwa5rSi9gtSjhVOi/v9RNYogBWW0mvySEEjm4XiThKwkMGw5W7I8foD+Q0ICz4q4yrNu7FnGmgk78E73q5Mo8qCCQVvPGoIobGXXXzD5My9PxZsvf6rO8GFG9VMlo/9NrYmtKOW51EZK4Zc4IkaucGEo+nBkoVA0bIl5NMHj05+VGaw504DOfzZxl4/Aehjd9Rsh9MK3MDtP69hu3IdsK2cWVAJhYKgwvOlM2diTvd6LFWUZbELeVRGpkqD8vRgoWhBUGGGsoVijhYYd7we69c/2hsznYRthG+lCsaaI4AKWMKdCu8l0pwJuOeG3JWS7tyhveUQ6IJ+vP3MrxS+lar0WK8KWEKm7LX9fMWZHrzU5reinY53ssTl/KB7JTFyKu16qIBlNdsvcGGo9ySgN+5ke0SzP1kv6MJVc7+HJDUbnV8M/iKw4AXmjK4Clqy7q2+vnyOz/9xy4o6RD3N6/YSSIf1KuVrrFcO2V/LoKmDJ+0bedWznObngVd8icQfEuXt4/ORH3n07JUMKevNuwZj2evrR2sfCaYUA8hIg6CXykHc42XdG9hzTDJiYV+dnw/sn6hmyP5atERSupx8tsNg/5Idi7dzcgkmz0iawi8QdMyhIsQvPX3eOobKeIUMjylujenFZCyyWJgHRGwtQQfJX17Mo+2MhQHJDMopxTuaWTn/QkIFNvZ4hQ+MKGa2GfqwYWmAxAGx5wxOHkv/qtddhGLjtVOcULEwUxsJkx4WqyBtB9pT5Be8tcNNtQv305VQECyWGwMI2uOiE0dCbRsx0VwamwbSpr1NvDe4h/48zMoWQCRBypn6807SVimAJr65neJzW80fT/GQemHrVN0FxdLXJwcekTVaV3gI84RhYY2iDsreNXqXgrhhU9QkLRbDQI6KHfpz4auxPA5bXXTF6ZEIDTMY5TUubvFgAE1jDk2q48Q7tVgoP4yNbwojhDmrKimAxAKYNgYU5mXaS905592kG3ZWFiQLC9FUztgaUGZS32PNX1WaRgpl1Emqs/bZ9XbBYFvvf+r/3AbnzZBcxCVhej4WrcMcyZQNTqrSJPoHJdU79EResEb7igdiYRlU2XbA2Ezi8J+S0MDP/vQTEz5kevDfCbAKhlDaZSMeTZ8wxXtpsLUk6vXmnEQDhtcXWBYtp4JNCYHH0yVMfz3xTntddYW9oY9z1pU2RaAruih4yPBCmDhZpljGzVyPznZZ3XULVoyc+9I44qpJlbdKmTUwv0jN5p4NWhetBzMGkvCcmrFQHC1n5OXXe5xkSmn2HF48dCh0drPc+KjN4ltAAgIpNmwSx3UPyr34SQzIskhxgMRPo8WZCqIO1NTnTCiVYrpZjyi5MxaZNMROhzcZdnbkYaszs5C+rhU4cW58DLGSSndbx1z7iC1VjRae9N8GK78f1TBkWcbxgc1o+++anwu5JHneF/JnAkp3WJtGetKclPModsg0A1Zg2habTqSdn9yadphlzz+OuGC4TWIwkO61n3voMe4/dV4x0MzRznVPHGKv5iKMi5RCmA1WRGhM6iTyUD6zNPYRwpoVSYGtsFo8jDF1XuzDVnjZF2hKqhCCIQrK5KwTOBxYT49dyHz95NqQmsnjceOQNPtMJfghH6C5T1zNlW52hGeWsR3WhNWbEQFE5FXLtlStXcs6f33fgsiU0IjN//eE7x86fZUoWz/sOoWrsuSFJ6qpHA+zbCYpluQrvgdaYbG6weAzr/lfeE2aCCnilrNCgHeprgDgg7IjSHpWOCgX9IcbW5L7PRXouR/pBlz52hqtvTwSUqULhmalC57nBYkiCPdm0YG9+IEm4ZhZO3MJDKMpNMfsaIDdA4f167ZoFwGKqZPHyxPhSnt4XpuWhKzqKiriLL1wJMpfMObvV3gJgMTZbD/axFiuKWzDZqKwyt/0WllHOZvmFvzuPTgiCcuKhp7dlwGI+PPDORZwwsVRPKAhD1HsIqrivL1wGMjWzF7PUHBcDiwlzASxPG8UlefpFHqW6o4YqOWEn33jq3psX3HxZEiyuEAe32klOG1sd9Lm4kamiPalV/itBV84lwUKOwWSLNo0ta7CNrzr1ifBErmm5YGplRV0YLOQg2RpcW40tFGUi4CBVy6ZWBYGFKCRbciJPG9jidhDKtaJvVSEmr0IhqFH4KbycGst9Syc0NxQHN/LFs1EcFI59uiY0aC31KIedBZaWLDDbzlBViHJKAQuVsd330F+HfRJXOrA1GD1lG1R0FJ7YBR1ccqWppSCwMDb7C/itGKtz1bPInYoY2RK24T6g/JSVGQuqnr/vFq6EEg49s6uywGIy8Xk6TgvXteBWzUzVy6dHJlV0UiBVSFUcWMhk9kXRrKx6o9NVhsXI8Gc0UJqvMlYrESwkgy2+ujOYWJg5sG2z1K1WI0DCvywnYh97CrWvq0LBwlRoVn4qsmNOFi6PwHcq6/rIV5V48D8GKebFzkLJF8jlgoXuxrKFrp87euvglliBtBH7+D4gfjpStnL2q0ICFw2WEZpbY/LXBDpz4+Lo6XtuKmQ7pyNb/yNIceMvMvaZ03niiAfaCr9qqQAstEmMEN7+0LcWNeXjBVIsGL4GEhn7mBQwkU0u9YiVV8+hyjrAQnrCBPuE8cHCTJgtCcxQ1AYPGDGLP7z9OWCFrOKtryvQVwOW0fXYsGjOYqETPkjtl02/IAn/ROAbuzyYRRXhz10PlYGF6IRFLsgjdyLcqVLGgfFFfnxYzgQFF0UKhdgTeELmisKfq+36wDLST3NddubcryU+Gs40IAMmnBMkTfNPRk4E40Wb5efpVqtuoVawmANmm5B1uZM3ZfDiV0a5ity8lWTP9dMuJ7mDjhPlF3IpgBTl+JS8LxI1SMWzxcvGbq9gkZUVg2VmyIUV3itytpHNwOvIwd37d+7AZxh/Rg3nUgaXc5e/2/y9escJgPj59PkYuYIxCpd+JFVmaPdQReXqwTK61sArvxXXgZTR20rAsnjx+k38R34mZo64JqRWCFaNeBFkHztyY+2Br7+uVuWx3OlxOcZPoQx+9cA9JWcZF0Vizu+B8bfqXCqktNWCZSdM+gVkY7e57enJC1zusdOxPhfVUdT6wTIT5iIO77UUYcY/sZ0BUvXuIHTQkT9uC1iuFsymJbtNqm7MhYlEapXxztVqp7yNYLkqMJCxq/nBxcvsuJrdKbdBfNmQxF7rXQd3HTmwe63JU6RCth2svppAbbP/eXUXlKMwx18+nr/0LQXedGo2TilDkvFDZst+23wSGhD+NbAE5bRD0zWw/LsbpsvezixYAw2sgo1Ts2gNrJqtV7DsDayCjVOzaA2smq1XsOwNrIKNU7NoDayarVew7A2sgo1Ts2gNrJqtV7DsDayCjVOzaA2smq1XsOwNrIKNU7NoDayarVew7A2sgo1Ts2gNrJqtV7DsDayCjVOzaA2smq1XsOwNrIKNU7NoDayarVew7A2sgo1Ts2gNrJqtV7DsDayCjVOzaA2smq1XsOwNrIKNU7NoDayarVew7A2sgo1Ts2j/BUv9rCzFndKYAAAAAElFTkSuQmCC
