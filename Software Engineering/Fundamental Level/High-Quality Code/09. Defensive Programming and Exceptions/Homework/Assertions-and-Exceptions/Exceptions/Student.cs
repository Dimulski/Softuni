﻿using System;
using System.Linq;
using System.Collections.Generic;

public class Student
{
    private string firstName;
    private string lastName;
    private IList<Exam> exams;

    public string FirstName
    {
        get { return this.firstName; }
        set
        {
            if (string.IsNullOrEmpty(value))
            {
                throw new ArgumentNullException("firstName cannot be null!", "firstName");
            }
            this.firstName = value;
        }
    }

    public string LastName
    {
        get { return this.lastName; }
        set
        {
            if (string.IsNullOrEmpty(value))
            {
                throw new ArgumentNullException("lastName cannot be null!", "lastName");
            }
            this.lastName = value;
        }
    }

    public IList<Exam> Exams
    {
        get { return exams.Select(x => x).ToList(); }
        set
        {
            if (value == null)
            {
                throw new ArgumentNullException("exams cannot be null!", "exams");
            }
            this.exams = value.Select(x => x).ToList();
        }
    }

    public Student(string firstName, string lastName, IList<Exam> exams = null)
    {
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Exams = exams;
    }

    public IList<ExamResult> CheckExams()
    {
        if (this.Exams == null)
        {
            throw new ArgumentException("Exams cannot be null!", "Exams");
        }

        if (this.Exams.Count == 0)
        {
            return null;
        }

        IList<ExamResult> results = new List<ExamResult>();
        for (int i = 0; i < this.Exams.Count; i++)
        {
            results.Add(this.Exams[i].Check());
        }

        return results;
    }

    public double CalcAverageExamResultInPercents()
    {
        if (this.Exams == null)
        {
            // Cannot calculate average on missing exams
            throw new ArgumentNullException("Exams cannot be null!", "Exams");
        }

        if (this.Exams.Count == 0)
        {
            // No exams --> return -1;
            Console.WriteLine("The student has no exams!");
            return 0;
        }

        double[] examScore = new double[this.Exams.Count];
        IList<ExamResult> examResults = CheckExams();
        for (int i = 0; i < examResults.Count; i++)
        {
            examScore[i] = 
                ((double)examResults[i].Grade - examResults[i].MinGrade) / 
                (examResults[i].MaxGrade - examResults[i].MinGrade);
        }

        return examScore.Average();
    }
}
