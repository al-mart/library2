const fs = require('fs');
const chalk = require('chalk');

const addNote = (title, body) => {
    const notes = loadNotes();
    const duplicateNote = notes.find((note) => {
        return note.title === title;
    });

    if (!duplicateNote) {
        notes.push({
            title,
            body
        });
        saveNotes(notes);
        console.log("Note added.");
    } else {
        console.log("Note title already taken.");
    }
};

const removeNote = (title) => {
    let notes = loadNotes();
    const noteIndex = notes.findIndex(note => note.title === title);

    if (noteIndex >= 0) {
        notes.splice(noteIndex, 1)
    }
    saveNotes(notes);
}

const readNote = (title) => {
    debugger;
    try {
        console.log(chalk.green.inverse(loadNotes().find(note => note.title === title).body));
    } catch (e) {
        console.log(chalk.red.inverse("No note found"));
    }
}

const loadNotes = () => {
    try {
        return JSON.parse(fs.readFileSync('notes.json', {encoding: 'utf-8'}));
    } catch (e) {
        return [];
    }
}

const saveNotes = (notes) => {
    const dataJson = JSON.stringify(notes);
    fs.writeFileSync('notes.json', dataJson);
}

exports.addNote = addNote;
exports.removeNote = removeNote;
exports.readNote = readNote;
