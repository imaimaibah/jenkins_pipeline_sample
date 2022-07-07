package com.foo.ci

def load(String path) {
    script = libraryResource (path)
    evaluate(script)
}

return this
