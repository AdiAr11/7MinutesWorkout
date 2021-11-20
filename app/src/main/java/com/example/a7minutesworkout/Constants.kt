package com.example.a7minutesworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        val exerciseList = ArrayList<ExerciseModel>()

        val jumpingJacks = ExerciseModel(1,
            "Jumping Jacks",
            R.drawable.ic_jumping_jacks,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(jumpingJacks)

        val abdominalCrunch = ExerciseModel(2,
        "Abdominal Crunch",
        R.drawable.ic_abdominal_crunch,
        isCompleted = false,
        isSelected = false
        )
        exerciseList.add(abdominalCrunch)

        val runningInPlace = ExerciseModel(3,
            "High Knees Running In Place",
            R.drawable.ic_high_knees_running_in_place,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(runningInPlace)

        val lunge = ExerciseModel(4,
            "Lunges",
            R.drawable.ic_lunge,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(lunge)

        val plank = ExerciseModel(5,
            "Plank",
            R.drawable.ic_plank,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(plank)

        val pushUps = ExerciseModel(6,
            "Push Ups",
            R.drawable.ic_push_up,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(pushUps)

        val pushUpAndRotation = ExerciseModel(7,
            "Push Ups and Rotation",
            R.drawable.ic_push_up_and_rotation,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(pushUpAndRotation)

        val sidePlank = ExerciseModel(8,
            "Side Plank",
            R.drawable.ic_side_plank,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(sidePlank)

        val squats = ExerciseModel(9,
            "Squats",
            R.drawable.ic_squat,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(squats)

        val stepOntoChair = ExerciseModel(10,
            "Step up onto chair",
            R.drawable.ic_step_up_onto_chair,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(stepOntoChair)

        val dipsOnChair = ExerciseModel(11,
            "Dips using chair",
            R.drawable.ic_triceps_dip_on_chair,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(dipsOnChair)

        val wallSit = ExerciseModel(12,
            "Wall Sit",
            R.drawable.ic_wall_sit,
            isCompleted = false,
            isSelected = false
        )
        exerciseList.add(wallSit)

        return  exerciseList
    }
}